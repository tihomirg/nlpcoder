package search2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

import nlp.parser.RelatedWordsMap;
import nlp.parser.one.WordCorrector;
import merging.CompositionStatistics;
import merging.core.GroupBuilder;
import merging.core.HandlerTable;
import merging.core.Merge;
import merging.core.SaturationGroupBuilder;
import merging.core.SaturationSynthesisGroup;
import merging.core.Synthesis;
import search.RichDeclaration;
import search.ScoreListener;
import search.ScorerPipeline;
import search.Search;
import search.nlp.parser.ComplexWordDecomposer;
import search.nlp.parser2.IParser;
import search.nlp.parser2.Input;
import search.nlp.parser2.ParserForComplexTokens;
import search.nlp.parser2.ParserForCorrectingWords;
import search.nlp.parser2.ParserForIndexes;
import search.nlp.parser2.ParserForLiterals;
import search.nlp.parser2.ParserForLocals;
import search.nlp.parser2.ParserForNaturalLanguage;
import search.nlp.parser2.ParserForRichLiteralsAndLocals;
import search.nlp.parser2.ParserForRightHandSideNeighbours;
import search.nlp.parser2.ParserForSemanticGraphNeighbours;
import search.nlp.parser2.ParserForWTokens;
import search.nlp.parser2.ParserPipeline;
import search.nlp.parser2.RichToken;
import search.nlp.parser2.Sentence;
import search.scorers.DeclProbScorer;
import search.scorers.HitWeightScorer;
import search.scorers.RichDeclarationScorer;
import statistics.posttrees.ConstructorInvocation;
import statistics.posttrees.Expr;
import statistics.posttrees.InstanceFieldAccess;
import statistics.posttrees.InstanceMethodInvocation;
import statistics.posttrees.LocalExpr;
import synthesis.ExprGroup;
import synthesis.PartialExpression;
import synthesis.PartialExpressionScorer;
import synthesis.comparators.PartialExpressionComparatorDesc;
import synthesis.handlers.HoleHandler;
import synthesis.handlers.LiteralHandler;
import types.NameGenerator;
import types.StabileTypeFactory;
import util.Pair;
import util.UtilList;
import api.StabileAPI;
import config.Config;
import definitions.Declaration;
import deserializers.Deserializer;
import deserializers.FrequencyDeserializer;
import deserializers.KryoDeserializer;
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
	private ParserForLocals parserForLocals;

	public SearchEngine(int maxNumOfSolutions) {
		this.maxNumOfSolutions = maxNumOfSolutions;
		load();
	}

	private void load() {
		time = System.currentTimeMillis();

		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		props.put("pos.model", "edu/stanford/nlp/models/pos-tagger/english-bidirectional/english-bidirectional-distsim.tagger");
		//props.put("pos.model", "edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger");
		
		StanfordCoreNLP coreNLP = new StanfordCoreNLP(props);
		ComplexWordDecomposer decomposer = new ComplexWordDecomposer(coreNLP);
		KryoDeserializer rwmDeserializer = new KryoDeserializer();
		RelatedWordsMap rwm = (RelatedWordsMap) rwmDeserializer.readObject(Config.getRelatedWordsMapLocation(), RelatedWordsMap.class);
		
		parserForLocals = new ParserForLocals();

		pipeline = new ParserPipeline(new IParser[]{
				new ParserForLiterals(),
				parserForLocals,
				new ParserForCorrectingWords(new WordCorrector()),
				new ParserForNaturalLanguage(coreNLP),
				new ParserForRichLiteralsAndLocals(),
				new ParserForSemanticGraphNeighbours(),
				new ParserForRightHandSideNeighbours(1),
				new ParserForComplexTokens(decomposer),
				new ParserForWTokens(rwm, 1.0, 0.5, 1.0),
				new ParserForIndexes()});


		NameGenerator nameGen = new NameGenerator(Config.getDeserializerVariablePrefix());
		Deserializer deserializer = new Deserializer();
		api = new StabileAPI(deserializer.deserialize(Config.getSecondStorageLocation()), nameGen);	

		//Loading statistics
		handlerTable = new HandlerTable();		
		CompositionStatistics stat = new CompositionStatistics(api.getStf(), api.getDeclsMap(), Config.getCompositionStatisticLocation(), handlerTable);
		stat.read();

		scorer = new ScorerPipeline(new RichDeclarationScorer[]{new HitWeightScorer(), new DeclProbScorer(0.5)});

		maxDeclarationPerLevel = 10;
		numOfSynthesisLevels = 5;

		listener = new ScoreListener(maxDeclarationPerLevel);

		//Test matrix
		indexScoress = new int[][]{{5, 1},{3, 3}};


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

			List<RichToken> searchKeyGroups = sentence.getSearchKeyRichTokens();

			for (RichToken group : searchKeyGroups) {
				rdss.add(search.search(group));
			}

			time = printMsgAndSetTime("Decl search time", time);

			if (SYNTHESIS) {
				List<List<ExprGroup>> exprGroupss = createExprGroupss(rdss);

				if (!exprGroupss.isEmpty()) {
					setExprRelatedGroups(exprGroupss);

					HoleHandler hHandler = new HoleHandler();
					List<Expr> strings = createStringLiterals(sentence.getStringLiteralRichTokens(), api.getStf());
					hHandler.addAllLocals(strings);
					List<Expr> numbers = createNumberLiterals(sentence.getNumberLiteralRichTokens(), api.getStf());
					hHandler.addAllLocals(numbers);					

					handlerTable.setHoleHandler(hHandler);
					
					LiteralHandler sHandler = new LiteralHandler();
					sHandler.addAllLocals(strings);
					handlerTable.setStringLiteralHandler(sHandler);
					
					LiteralHandler nHandler = new LiteralHandler();
					nHandler.addAllLocals(numbers);					
					handlerTable.setNumberLiteralHandler(nHandler);
					
					int inputSize = searchKeyGroups.size() + strings.size() + numbers.size();
					
					PartialExpressionScorer peScorer = new PartialExpressionScorer(3, 3, inputSize, 3);
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
						List<PartialExpression> completedResult = merge.getCompletedResult();

						fixScore(completedResult, strings, numbers);

						solutions.addAll(completedResult);
					}

					fixScore(completed, strings, numbers);
					solutions.addAll(completed);

				}
			}

			printMsgAndSetTime("Expression Synthesis time", time);
		}

		return prepare(solutions);
	}

	private void fixScore(List<PartialExpression> pexps, List<Expr> strings, List<Expr> numbers) {
		if(!strings.isEmpty()){
			for (PartialExpression pexp : pexps) {
				String stringRep = pexp.repToString();

				fix(strings, pexp, stringRep);

			}
		}

		if(!numbers.isEmpty()){
			for (PartialExpression pexp : pexps) {
				String stringRep = pexp.repToString();

				fix(numbers, pexp, stringRep);
			}
		}

	}

	private void fix(List<Expr> literals, PartialExpression pexp, String stringRep) {
		int repetitions = numOfRepetitions(stringRep, literals);

		if (repetitions > literals.size()){
			System.out.println(stringRep+"  str: "+repetitions +"  "+literals);
			pexp.setScore(pexp.getScore() - 2*(repetitions - literals.size()));
		}
	}

	private int numOfRepetitions(String stringRep, List<Expr> exprs) {
		int sum = 0;
		Set<String> visited = new HashSet<String>();
		for (Expr expr : exprs) {
			String prefix = expr.getPrefix();
			System.out.println("Prefix: "+prefix);
			
			if(!visited.contains(prefix)){
				visited.add(prefix);
				sum += numOfRepetitions(stringRep, prefix);
			}
		}

		return sum;
	}

	private int numOfRepetitions(String stringRep, String prefix) {
		String current = stringRep;

		int sum = 0;
		while(true){
			int val = current.indexOf(prefix);
			if (val != -1){
				sum++;
				current = current.substring(val+prefix.length());
			} else break;
		}

		return sum;
	}

	private List<Expr> createStringLiterals(List<RichToken> stringLiterals, StabileTypeFactory stf) {

		List<Expr> exprs = new LinkedList<Expr>();

		for (RichToken literal : stringLiterals) {
			String name = literal.getStringLiteral();
			LocalExpr localExpr = new LocalExpr(name, stf.createConstType(java.lang.String.class.getName()));
			localExpr.setLogProbability(2);

			exprs.add(localExpr);
		}

		return exprs;
	}

	private List<Expr> createNumberLiterals(List<RichToken> stringLiterals, StabileTypeFactory stf) {

		List<Expr> exprs = new LinkedList<Expr>();

		for (RichToken literal : stringLiterals) {
			String name = literal.getNumberLiteral();
			LocalExpr localExpr = new LocalExpr(name, stf.createPrimitiveType("int"));
			localExpr.setLogProbability(2);

			exprs.add(localExpr);
		}

		return exprs;
	}	

	private String[] prepare(PriorityQueue<PartialExpression> solutions) {
		List<String> results = new LinkedList<String>();
		int i = 0;

		for (; i < maxNumOfSolutions && !solutions.isEmpty(); i++) {
			PartialExpression pexpr = solutions.remove();
			results.add(pexpr.repToString());
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

		for(int i = 0; i < rdss.size(); i++){
			List<RichDeclaration> rds = rdss.get(i);
			egroupss.add(createExprGroups(rds, i));
		}

		return egroupss;
	}

	private static List<ExprGroup> createExprGroups(List<RichDeclaration> rds, int index) {
		List<ExprGroup> egroups = new LinkedList<ExprGroup>();
		for (RichDeclaration rd : rds) {
			egroups.add(new ExprGroup(createExpr(rd.getDecl()), index, rd.getScore().getSum()));				
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

	public static void main(String[] args) {
		SearchEngine se = new SearchEngine(50);

		Scanner scanner = new Scanner(System.in);

		System.out.print("Input: ");

		String line = null;
		while((line = scanner.nextLine()) != null){

			if (scanner.equals("exitexit")) break;

			String[] results = se.run(line);
			System.out.println();
			System.out.println();
			for (String result: results) {
				System.out.println(result);
			}

			System.out.print("Input: ");
		}

		scanner.close();
	}
	
	public StabileAPI getAPI() {
		return api;
	}
	
	public ParserForLocals getParserForLocals() {
		return parserForLocals;
	}
}
