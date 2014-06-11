package search;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import config.Config;
import definitions.Declaration;
import deserializers.Deserializer;
import deserializers.FrequencyDeserializer;
import api.StabileAPI;
import search.scorers.DeclProbScorer;
import search.scorers.HitWeightScorer;
import search.scorers.RichDeclarationScorer;
import statistics.CompositionStatistics;
import statistics.posttrees.ConstructorInvocation;
import statistics.posttrees.Expr;
import statistics.posttrees.InstanceFieldAccess;
import statistics.posttrees.InstanceMethodInvocation;
import synthesis.ExprGroup;
import synthesis.PartialExpression;
import synthesis.PartialExpressionScorer;
import synthesis.core.GroupBuilder;
import synthesis.core.HandlerTable;
import synthesis.core.Merge;
import synthesis.core.SaturationGroupBuilder;
import synthesis.core.SaturationSynthesisGroup;
import synthesis.core.Synthesis;
import types.NameGenerator;
import util.Pair;
import util.UtilList;
import nlp.parser.ComplexWordDecomposer;
import nlp.parser.Group;
import nlp.parser.IParser;
import nlp.parser.Input;
import nlp.parser.ParserAssignTokenScores;
import nlp.parser.ParserExtractLiterals;
import nlp.parser.ParserGroupsAndDependencyRelations;
import nlp.parser.ParserIdentifyLocals;
import nlp.parser.ParserNLP;
import nlp.parser.ParserPipeline;
import nlp.parser.ParserRelatedWords;
import nlp.parser.ParserRightHandSideNeighbours;
import nlp.parser.ParserSliceComplexTokens;
import nlp.parser.ParserWTokensAndLevels;
import nlp.parser.Sentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class MainAll {
	public static void main(String[] args) {

		long time = System.currentTimeMillis();

		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		StanfordCoreNLP coreNLP = new StanfordCoreNLP(props);

		Map<String, Local> locals = new HashMap<String, Local>();
		ComplexWordDecomposer decomposer = new ComplexWordDecomposer(coreNLP);

		ParserPipeline pipeline = new ParserPipeline(
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
		StabileAPI api = new StabileAPI(deserializer.deserialize(Config.getSecondStorageLocation()), nameGen);	

		//Loading statistics
		HandlerTable handlerTable = new HandlerTable();		
		CompositionStatistics stat = new CompositionStatistics(api.getStf(), api.getDeclsMap(), Config.getCompositionStatisticLocation(), handlerTable);
		stat.read();

		ScorerPipeline scorer = new ScorerPipeline(new RichDeclarationScorer[]{new HitWeightScorer(), new DeclProbScorer()});

		int maxDeclarationPerLevel = 10;
		int numOfSynthesisLevels = 5;
		
		ScoreListener listener = new ScoreListener(maxDeclarationPerLevel);

		//Test matrix
		int[][] indexScoress = {{5, 3},{1, 2}};


		//Up till now we loaded all

		//Input input = pipeline.parse(new Input("open(file(\"text.txt\"), make)"));
		//Input input = pipeline.parse(new Input("open read close a file \"text.txt\""));

		FrequencyDeserializer frequencies = new FrequencyDeserializer(Config.getDeclarationFrequencyLocation());
		
		System.out.println(frequencies);
		
		Scanner scanner = new Scanner(System.in);

		String line = null;
		
		time = printMsgAndSetTime("Loading time",time);
		
		System.out.print("Input: ");
		while((line = scanner.nextLine()) != null){
			if (line.equals("exit")) break;
			else {
				Input input = pipeline.parse(new Input(line));

				//Input input = pipeline.parse(new Input("new InputStream(new Files(\"text.txt\"))"));


				//Input input = pipeline.parse(new Input("Transfer contents of a file \"text1.txt\" to a file \"text2.txt\""));
				//Input input = pipeline.parse(new Input("Create a window."));
				//Input input = pipeline.parse(new Input("Read bytes from an input stream."));
				//Input input = pipeline.parse(new Input("Bell, based in Los Angeles, makes and distributes electronic, computer and building products."));
				//Input input = pipeline.parse(convert a String to an int)
				//Input input = pipeline.parse(compare Strings in java)
				//Input input = pipeline.parse(new Input("Load a file \"text1.txt\" content into a buffer."));

				Search search = new Search(scorer, listener, api, indexScoress, frequencies);

				System.out.println("Input parsing time : "+(System.currentTimeMillis() - time)+" ms");		
				time = System.currentTimeMillis();

				List<Sentence> sentences = input.getSentences();

				for (Sentence sentence : sentences) {
					List<List<RichDeclaration>> rdss = new LinkedList<List<RichDeclaration>>();
					
					for (Group group : sentence.getSearchKeyGroups()) {
						rdss.add(search.search(group));
					}	

					time = printMsgAndSetTime("Decl search time", time);
					//TODO: Here we do synthesis, it is per sentence.


					//Loading test declarations
					List<List<ExprGroup>> exprGroupss = createExprGroupss(rdss);
					setExprRelatedGroups(exprGroupss);


					PartialExpressionScorer peScorer = new PartialExpressionScorer();
					GroupBuilder<SaturationSynthesisGroup> builder = new SaturationGroupBuilder(handlerTable, peScorer, numOfSynthesisLevels, maxDeclarationPerLevel);
					Synthesis<SaturationSynthesisGroup> synthesis = new Synthesis<SaturationSynthesisGroup>(exprGroupss, builder, false);
					synthesis.run();

					System.out.println(synthesis);

					Pair<List<PartialExpression>, List<PartialExpression>> pexprs = synthesis.getPexprs();

					final List<PartialExpression> withConnections = pexprs.getFirst();
					List<PartialExpression> completed = pexprs.getSecond();

					prepareForMearging(completed);
					prepareForMearging(withConnections);

					//List<PartialExpression> list = new LinkedList<PartialExpression>(){{add(withConnections.get(0));}};

					Merge merge = new Merge(withConnections, 4, numOfSynthesisLevels, maxDeclarationPerLevel, peScorer, false);

					merge.run();

					System.out.println(merge);			
				}

				time = printMsgAndSetTime("Expression Synthesis time", time);
			}
			
			System.out.print("Input: ");
		}

		scanner.close();
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
