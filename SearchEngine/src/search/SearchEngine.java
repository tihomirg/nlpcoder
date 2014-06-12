package search;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Properties;

import merging.CompositionStatistics;
import merging.core.GroupBuilder;
import merging.core.HandlerTable;
import merging.core.Merge;
import merging.core.SaturationGroupBuilder;
import merging.core.SaturationSynthesisGroup;
import merging.core.Synthesis;
import search.nlp.parser.ComplexWordDecomposer;
import search.nlp.parser.Group;
import search.nlp.parser.IParser;
import search.nlp.parser.Input;
import search.nlp.parser.ParserAssignTokenScores;
import search.nlp.parser.ParserExtractLiterals;
import search.nlp.parser.ParserGroupsAndDependencyRelations;
import search.nlp.parser.ParserIdentifyLocals;
import search.nlp.parser.ParserNLP;
import search.nlp.parser.ParserPipeline;
import search.nlp.parser.ParserRelatedWords;
import search.nlp.parser.ParserRightHandSideNeighbours;
import search.nlp.parser.ParserSliceComplexTokens;
import search.nlp.parser.ParserWTokensAndLevels;
import search.nlp.parser.Sentence;
import search.scorers.DeclProbScorer;
import search.scorers.HitWeightScorer;
import search.scorers.RichDeclarationScorer;
import statistics.posttrees.ConstructorInvocation;
import statistics.posttrees.Expr;
import statistics.posttrees.InstanceFieldAccess;
import statistics.posttrees.InstanceMethodInvocation;
import synthesis.ExprGroup;
import synthesis.PartialExpression;
import synthesis.PartialExpressionScorer;
import synthesis.comparators.PartialExpressionComparatorDesc;
import types.NameGenerator;
import util.Pair;
import util.UtilList;
import api.StabileAPI;
import config.Config;
import definitions.Declaration;
import deserializers.Deserializer;
import deserializers.FrequencyDeserializer;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class SearchEngine {

	private static final boolean SYNTHESIS = true;
	private long time;
	private ParserPipeline pipeline;
	private StabileAPI api;
	private ScorerPipeline scorer;
	private int[][] indexScoress;
	private ScoreListener listener;
	private FrequencyDeserializer frequencies;
	private int maxDeclarationPerLevel;
	private int numOfSynthesisLevels;
	private HandlerTable handlerTable;
	private int maxNumOfSolutions;
	
	public SearchEngine(int maxNumOfSolutions) {
		this.maxNumOfSolutions = maxNumOfSolutions;
		load();
	}

	private void load() {
		time = System.currentTimeMillis();

		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		StanfordCoreNLP coreNLP = new StanfordCoreNLP(props);

		Map<String, Local> locals = new HashMap<String, Local>();
		ComplexWordDecomposer decomposer = new ComplexWordDecomposer(coreNLP);

		pipeline = new ParserPipeline(
				new IParser[]{ 
						new ParserNLP(coreNLP),
						new ParserGroupsAndDependencyRelations(),
						new ParserExtractLiterals(),
						new ParserIdentifyLocals(locals),					
						new ParserRightHandSideNeighbours(1),
						new ParserSliceComplexTokens(decomposer),						
						new ParserRelatedWords(),
						new ParserWTokensAndLevels(),
						new ParserAssignTokenScores(new double[]{1,0.5,1,0.5}, new int[]{0,0,1,1})});


		NameGenerator nameGen = new NameGenerator(Config.getDeserializerVariablePrefix());
		Deserializer deserializer = new Deserializer();
		api = new StabileAPI(deserializer.deserialize(Config.getSecondStorageLocation()), nameGen);	

		//Loading statistics
		handlerTable = new HandlerTable();		
		CompositionStatistics stat = new CompositionStatistics(api.getStf(), api.getDeclsMap(), Config.getCompositionStatisticLocation(), handlerTable);
		stat.read();

		scorer = new ScorerPipeline(new RichDeclarationScorer[]{new HitWeightScorer(), new DeclProbScorer(0.4)});

		maxDeclarationPerLevel = 10;
		numOfSynthesisLevels = 5;

		listener = new ScoreListener(maxDeclarationPerLevel);

		//Test matrix
		indexScoress = new int[][]{{5, 3},{1, 2}};


		//Up till now we loaded all

		//Input input = pipeline.parse(new Input("open(file(\"text.txt\"), make)"));
		//Input input = pipeline.parse(new Input("open read close a file \"text.txt\""));

		frequencies = new FrequencyDeserializer(Config.getDeclarationFrequencyLocation());

		System.out.println(frequencies);

	}

	public String[] run(String line){

		PriorityQueue<PartialExpression> solutions = new PriorityQueue<PartialExpression>(100, new PartialExpressionComparatorDesc());
		
		time = System.currentTimeMillis();
		Input input = pipeline.parse(new Input(line));

		Search search = new Search(scorer, listener, api, indexScoress, frequencies);
		printMsgAndSetTime("Input parsing time",time);

		List<Sentence> sentences = input.getSentences();

		for (Sentence sentence : sentences) {
			List<List<RichDeclaration>> rdss = new LinkedList<List<RichDeclaration>>();

			for (Group group : sentence.getSearchKeyGroups()) {
				rdss.add(search.search(group));
			}	

			time = printMsgAndSetTime("Decl search time", time);

			if (SYNTHESIS) {
				List<List<ExprGroup>> exprGroupss = createExprGroupss(rdss);
				setExprRelatedGroups(exprGroupss);

				PartialExpressionScorer peScorer = new PartialExpressionScorer(5, 1);
				GroupBuilder<SaturationSynthesisGroup> builder = new SaturationGroupBuilder(handlerTable, peScorer, numOfSynthesisLevels, maxDeclarationPerLevel);
				Synthesis<SaturationSynthesisGroup> synthesis = new Synthesis<SaturationSynthesisGroup>(exprGroupss, builder, false);
				synthesis.run();

				System.out.println(synthesis);

				Pair<List<PartialExpression>, List<PartialExpression>> pexprs = synthesis.getPexprs();

				final List<PartialExpression> withConnections = pexprs.getFirst();
				List<PartialExpression> completed = pexprs.getSecond();

				prepareForMearging(completed);
				prepareForMearging(withConnections);

				if (!withConnections.isEmpty()){
					Merge merge = new Merge(withConnections, 4, numOfSynthesisLevels, maxDeclarationPerLevel, peScorer, false);
					merge.run();
					solutions.addAll(merge.getCompletedResult());
				} 
				solutions.addAll(completed);
			}

			printMsgAndSetTime("Expression Synthesis time", time);
		}
		
		return prepare(solutions);
	}

	private String[] prepare(PriorityQueue<PartialExpression> solutions) {
		List<String> results = new LinkedList<String>();
		int i = 0;
		
		for (; i < maxNumOfSolutions || !solutions.isEmpty(); i++) {
			PartialExpression pexpr = solutions.remove();
			results.add(pexpr.toString());
		}
		
		for (;i < maxNumOfSolutions; i++){
			results.add("");
		}
		
		return results.toArray(new String[results.size()]);
	}
	
	private static PriorityQueue<PartialExpression> mergeAndSort(List<PartialExpression> list, List<PartialExpression> completed) {
		PriorityQueue<PartialExpression> pq = new PriorityQueue<PartialExpression>(100, new PartialExpressionComparatorDesc());

		pq.addAll(list);
		pq.addAll(completed);

		return pq;
	}

	private static long printMsgAndSetTime(String msg, long time) {
		System.out.println(msg+": "+(System.currentTimeMillis() - time)+" ms");
		return System.currentTimeMillis();
	}

	private static void prepareForMearging(List<PartialExpression> pexprs) {
		for (PartialExpression pexpr : pexprs) {
			pexpr.prepareForMearging();
		}
	}


	private static List<List<ExprGroup>> createExprGroupss(List<List<RichDeclaration>> rdss) {
		List<List<ExprGroup>> egroupss = new LinkedList<List<ExprGroup>>();

		for (List<RichDeclaration> rds : rdss) {
			List<ExprGroup> egroups = createExprGroups(rds);
			egroupss.add(egroups);
		}

		return egroupss;
	}

	private static List<ExprGroup> createExprGroups(List<RichDeclaration> rds) {
		List<ExprGroup> egroups = new LinkedList<ExprGroup>();
		for (RichDeclaration rd : rds) {
			egroups.add(new ExprGroup(createExpr(rd.getDecl()), rd.getScore().getSum()));				
		}
		return egroups;
	}

	public static void setExprRelatedGroups(List<List<ExprGroup>> exprGroupss){

		for (List<ExprGroup> eGroups : exprGroupss) {			
			List<List<ExprGroup>> relatedGroupss = new LinkedList<List<ExprGroup>>();
			relatedGroupss.addAll(exprGroupss);
			relatedGroupss.remove(eGroups);

			for (ExprGroup eGroup : eGroups) {
				eGroup.setRelatedGroups(UtilList.flatten(relatedGroupss));
			}
		}
	}

	private static Expr createExpr(Declaration decl) {
		if (decl.isMethod()){
			if (decl.isConstructor()){
				return new ConstructorInvocation(decl);
			} else {
				return new InstanceMethodInvocation(decl);
			}
		} else {
			return new InstanceFieldAccess(decl);			
		}
	}	
}
