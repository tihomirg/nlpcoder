package search;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

import nlp.parser.RelatedWordsMap;
import nlp.parser.Token;
import nlp.parser.one.WordCorrector;
import merging.CompositionStatistics;
import merging.core.GroupBuilder;
import merging.core.HandlerTable;
import merging.core.Merge;
import merging.core.SaturationGroupBuilder;
import merging.core.SaturationSynthesisGroup;
import merging.core.Synthesis;
import search.config.SearchConfig;
import search.nlp.parser.APIWords;
import search.nlp.parser.ComplexWordDecomposer;
import search.nlp.parser.IParser;
import search.nlp.parser.Input;
import search.nlp.parser.ParserForComplexTokens;
import search.nlp.parser.ParserForCorrectingWords;
import search.nlp.parser.ParserForDisjointSubgroups;
import search.nlp.parser.ParserForLiterals;
import search.nlp.parser.ParserForLocals;
import search.nlp.parser.ParserForNaturalLanguage;
import search.nlp.parser.ParserForRichLiteralsAndLocals;
import search.nlp.parser.ParserForRightHandSideNeighbours;
import search.nlp.parser.ParserForSemanticGraphNeighbours;
import search.nlp.parser.ParserForWeightsAndImportanceIndexes;
import search.nlp.parser.ParserPipeline;
import search.nlp.parser.RichToken;
import search.nlp.parser.Sentence;
import search.nlp.parser.WordPosCorrector;
import search.scorers.UnigramScorer;
import search.scorers.HungarianScorer;
import search.scorers.RichDeclarationScorer;
import statistics.posttrees.ConstructorInvocation;
import statistics.posttrees.Expr;
import statistics.posttrees.InstanceFieldAccess;
import statistics.posttrees.InstanceMethodInvocation;
import statistics.posttrees.InputExpr;
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
import util.time.TimeStatistics;
import api.Local;
import api.StabileAPI;
import config.Config;
import definitions.Declaration;
import deserializers.Deserializer;
import deserializers.Unigram;
import deserializers.KryoDeserializer;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class ISText {
	private TimeStatistics time;
	private ParserPipeline pipeline;
	private StabileAPI api;
	private ScorerPipeline scorer;
	private SelectListener listener;
	private Unigram frequencies;
	private HandlerTable handlerTable;
	private int maxNumOfSolutions;
	private ParserForLocals parserForLocals;
	private DeclarationSearchEngine search;

	public ISText(int maxNumOfSolutions) {
		this.maxNumOfSolutions = maxNumOfSolutions;
		this.time = new TimeStatistics();
		load();
	}

	private void load() {
		this.time.startMeasuringTime("Loading time");

		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		//props.put("pos.model", "edu/stanford/nlp/models/pos-tagger/english-bidirectional/english-bidirectional-distsim.tagger");
		//props.put("pos.model", "edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger");

		StanfordCoreNLP coreNLP = new StanfordCoreNLP(props);
		WordPosCorrector posCorrector = new WordPosCorrector();

		ComplexWordDecomposer decomposer = new ComplexWordDecomposer(coreNLP, posCorrector);
		KryoDeserializer rwmDeserializer = new KryoDeserializer();
		RelatedWordsMap rwm = (RelatedWordsMap) rwmDeserializer.readObject(Config.getRelatedWordsMapLocation(), RelatedWordsMap.class);

		NameGenerator nameGen = new NameGenerator(Config.getDeserializerVariablePrefix());
		Deserializer deserializer = new Deserializer();
		api = new StabileAPI(deserializer.deserialize(Config.getSecondStorageLocation()), nameGen);	

		parserForLocals = new ParserForLocals();
		pipeline = new ParserPipeline(new IParser[]{
				new ParserForLiterals(),
				parserForLocals,
				new ParserForCorrectingWords(new WordCorrector(), new APIWords(api)),
				new ParserForNaturalLanguage(coreNLP, posCorrector),
				new ParserForRichLiteralsAndLocals(),
				new ParserForSemanticGraphNeighbours(),
				new ParserForRightHandSideNeighbours(),
				new ParserForComplexTokens(decomposer),
				new ParserForWeightsAndImportanceIndexes(rwm),
				new ParserForDisjointSubgroups()});

		//Loading statistics
		handlerTable = new HandlerTable();		
		CompositionStatistics stat = new CompositionStatistics(api.getStf(), api.getDeclsMap(), Config.getCompositionStatisticLocation(), handlerTable);
		stat.read();
		
		System.out.println(stat);

		listener = new SelectListener();

		scorer = new ScorerPipeline(
				new RichDeclarationScorer[]{
						new HungarianScorer(), 
						new UnigramScorer()});

		frequencies = new Unigram(Config.getDeclarationFrequencyLocation());
		System.out.println(frequencies);

		this.search = new DeclarationSearchEngine(
				scorer, 
				listener, 
				api, 
				frequencies, 
				SearchConfig.getMaxSelectedDeclarations(), 
				SearchConfig.getPrimaryIndex(), 
				SearchConfig.getPrimaryWeight(), 
				SearchConfig.getSecondaryIndex(), 
				SearchConfig.getSecondaryWeight());

		time.stopMeasuringTime();
	}
	
	public List<SearchReport> runForDeclarationSearch(String line, List<Local> locals) {
		this.parserForLocals.setLocals(locals);

		this.search = new DeclarationSearchEngine(
				scorer, 
				listener, 
				api, 
				frequencies, 
				SearchConfig.getMaxSelectedDeclarations(), 
				SearchConfig.getPrimaryIndex(), 
				SearchConfig.getPrimaryWeight(), 
				SearchConfig.getSecondaryIndex(), 
				SearchConfig.getSecondaryWeight());
		
		Input input = pipeline.parse(new Input(line));
		List<Sentence> sentences = input.getSentences();

		List<SearchReport> reports = new LinkedList<SearchReport>();
		for (Sentence sentence : sentences) {
			List<RichToken> searchKeyGroups = sentence.getSearchKeyRichTokens();

			for (RichToken richToken : searchKeyGroups) {
				reports.add(search.search(richToken));
			}
		}
		
		return reports;
	}
	
	
	public List<Token> runForLeadingTokens(String line, List<Local> locals){
		this.parserForLocals.setLocals(locals);
		Input input = pipeline.parse(new Input(line));
		
		List<Sentence> sentences = input.getSentences();
		List<Token> leadingTokens = new LinkedList<Token>();
		for (Sentence sentence : sentences) {
			List<RichToken> searchKeyGroups = sentence.getSearchKeyRichTokens();
			
			for (RichToken richToken : searchKeyGroups) {
				leadingTokens.addAll(richToken.getLeadingTokens());
			}
		}
		
		return leadingTokens;
	}
	
	public List<Token> runForAllRichTokens(String line, List<Local> locals){
		this.parserForLocals.setLocals(locals);
		Input input = pipeline.parse(new Input(line));
		
		List<Sentence> sentences = input.getSentences();
		List<Token> leadingTokens = new LinkedList<Token>();
		for (Sentence sentence : sentences) {
			List<RichToken> allRichTokens = sentence.getRichTokens();
			
			for (RichToken richToken : allRichTokens) {
				leadingTokens.addAll(richToken.getLeadingTokens());
			}
		}
		
		return leadingTokens;
	}	
	
	public String[] run(String line, List<Local> locals) {
		
		PriorityQueue<PartialExpression> solutions = new PriorityQueue<PartialExpression>(100, new PartialExpressionComparatorDesc());

		time.startMeasuringTime("Input Parsing time");
		this.parserForLocals.setLocals(locals);
		Input input = pipeline.parse(new Input(line));
		time.stopMeasuringTime();

		List<Sentence> sentences = input.getSentences();

		for (Sentence sentence : sentences) {
			List<SearchReport> reports = new LinkedList<SearchReport>();

			List<RichToken> searchKeyGroups = sentence.getSearchKeyRichTokens();

			this.time.startMeasuringTime("Declaration Search time");
			for (RichToken richToken : searchKeyGroups) {
				reports.add(search.search(richToken));
			}			
			this.time.stopMeasuringTime();

			//printSearchReports(reports);

			if (SearchConfig.isSynthesis()) {
				//Main expressions
				List<List<ExprGroup>> exprGroupss = createExprGroupss(reports);

				if (!exprGroupss.isEmpty()) {
					this.time.startMeasuringTime("PCFG Syntehsis time");
					
					setExprRelatedGroups(exprGroupss);

					//Additional expressions
					List<Expr> strings = createStringLiterals(sentence.getStringLiteralRichTokens(), api.getStf());
					List<Expr> numbers = createNumberLiterals(sentence.getNumberLiteralRichTokens(), api.getStf());
					List<Expr> booleans = createBooleanLiterals(sentence.getBooleanLiteralRichTokens(), api.getStf());					
					List<Expr> localExprs = createLocals(sentence.getLocals());

					//Dealing with holes
					HoleHandler hHandler = new HoleHandler(SearchConfig.getHoleWeight());
					hHandler.addAllHoleReplacements(strings);
					hHandler.addAllHoleReplacements(numbers);
					hHandler.addAllHoleReplacements(localExprs);
					handlerTable.setHoleHandler(hHandler);
					
					//Dealing with string literals
					LiteralHandler sHandler = new LiteralHandler();
					sHandler.addAllLiterals(strings);
					handlerTable.setStringLiteralHandler(sHandler);

					//Dealing with number literals;
					LiteralHandler nHandler = new LiteralHandler();
					nHandler.addAllLiterals(numbers);
					handlerTable.setNumberLiteralHandler(nHandler);

					//Dealing with boolean literals;
					LiteralHandler bHandler = new LiteralHandler();
					bHandler.addAllLiterals(booleans);
					handlerTable.setBooleanLiteralHandler(bHandler);				
					
					int inputSize = searchKeyGroups.size() + strings.size() + numbers.size();

					PartialExpressionScorer peScorer = new PartialExpressionScorer(inputSize);
					GroupBuilder<SaturationSynthesisGroup> builder = new SaturationGroupBuilder(handlerTable, peScorer, SearchConfig.getNumberOfSynthesisLevels(), SearchConfig.getMaxPartialExpressionsPerSynthesisLevel());
					Synthesis<SaturationSynthesisGroup> synthesis = new Synthesis<SaturationSynthesisGroup>(exprGroupss, builder, SearchConfig.isParallelSynthesis());
					synthesis.run();

					this.time.stopMeasuringTime();

					//System.out.println(synthesis);

					this.time.startMeasuringTime("Merging Syntesis time");
					Pair<List<PartialExpression>, List<PartialExpression>> pexprs = synthesis.getPexprs();

					final List<PartialExpression> withConnections = pexprs.getFirst();
					List<PartialExpression> completed = pexprs.getSecond();

					prepareForMearging(completed);
					prepareForMearging(withConnections);

					if (!withConnections.isEmpty()){
						Merge merge = new Merge(withConnections, SearchConfig.getNumberOfMergeGroups(), SearchConfig.getNumberOfSynthesisLevels(), SearchConfig.getMaxPartialExpressionsPerSynthesisLevel(), peScorer, SearchConfig.isParallelSynthesis());
						merge.run();
						solutions.addAll(merge.getCompletedResult());
					}

					solutions.addAll(completed);
					this.time.stopMeasuringTime();
				}
			}
		}

		//System.out.println(this.time);

		return prepare(solutions);
	}

	private List<Expr> createBooleanLiterals(List<RichToken> booleanLiterals, StabileTypeFactory stf) {		
		List<Expr> exprs = new LinkedList<Expr>();

			for (RichToken literal : booleanLiterals) {
				String name = literal.getNumberLiteral();
				InputExpr localExpr = new InputExpr(name, stf.createPrimitiveType("boolean"));
				localExpr.setScore(SearchConfig.getBooleanLiteralWeight());

				exprs.add(localExpr);
			}

			return exprs;
	}

	private void printSearchReports(List<SearchReport> reports) {
		for (SearchReport report : reports) {
			System.out.println();
			System.out.println(report);
			System.out.println();
		}
	}

	private List<Expr> createLocals(List<RichToken> locals) {
		List<Expr> exprs = new LinkedList<Expr>();

		for (RichToken local : locals) {
			Local iLocal = local.getLocal();
			String name = iLocal.getName();
			InputExpr localExpr = new InputExpr(name, iLocal.getType());
			localExpr.setScore(SearchConfig.getLocalVariableWeight());

			exprs.add(localExpr);
		}

		return exprs;
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
			InputExpr localExpr = new InputExpr(name, stf.createConstType(java.lang.String.class.getName()));
			localExpr.setScore(SearchConfig.getStringLiteralWeight());

			exprs.add(localExpr);
		}

		return exprs;
	}

	private List<Expr> createNumberLiterals(List<RichToken> stringLiterals, StabileTypeFactory stf) {

		List<Expr> exprs = new LinkedList<Expr>();

		for (RichToken literal : stringLiterals) {
			String name = literal.getNumberLiteral();
			InputExpr localExpr = new InputExpr(name, stf.createPrimitiveType("int"));
			localExpr.setScore(SearchConfig.getNumberLiteralWeight());

			exprs.add(localExpr);
		}

		return exprs;
	}	

	private String[] prepare(PriorityQueue<PartialExpression> solutions) {
		List<String> results = new LinkedList<String>();
		int i = 0;

		for (; i < maxNumOfSolutions && !solutions.isEmpty(); i++) {
			PartialExpression pexpr = solutions.remove();
			results.add(pexpr.repToString()); //pexpr.getStatistics());
		}
		
		//System.out.println();
		//System.out.println();

		return results.toArray(new String[results.size()]);
	}

	private static PriorityQueue<PartialExpression> mergeAndSort(List<PartialExpression> list, List<PartialExpression> completed) {
		PriorityQueue<PartialExpression> pq = new PriorityQueue<PartialExpression>(100, new PartialExpressionComparatorDesc());

		pq.addAll(list);
		pq.addAll(completed);

		return pq;
	}

	private static void prepareForMearging(List<PartialExpression> pexprs) {
		for (PartialExpression pexpr : pexprs) {
			pexpr.prepareForMearging();
		}
	}

	private static List<List<ExprGroup>> createExprGroupss(List<SearchReport> reports) {
		List<List<ExprGroup>> egroupss = new LinkedList<List<ExprGroup>>();

		for(int i = 0; i < reports.size(); i++){
			List<RichDeclaration> rds = reports.get(i).getResults();
			egroupss.add(createExprGroups(rds, i));
		}

		return egroupss;
	}

	private static List<ExprGroup> createExprGroups(List<RichDeclaration> rds, int index) {
		List<ExprGroup> egroups = new LinkedList<ExprGroup>();
		for (RichDeclaration rd : rds) {
			egroups.add(new ExprGroup(createExpr(rd.getDecl()), index, rd.getScore().getCoefSum()));				
		}
		return egroups;
	}

	public static void setExprRelatedGroups(List<List<ExprGroup>> exprGroupss){

		for (List<ExprGroup> eGroups : exprGroupss) {			
			List<List<ExprGroup>> relatedGroupss = new LinkedList<List<ExprGroup>>();
			relatedGroupss.addAll(exprGroupss);
			relatedGroupss.remove(eGroups);

			List<ExprGroup> relatedGroups = UtilList.flatten(relatedGroupss);
			for (ExprGroup eGroup : eGroups) {
				eGroup.setRelatedGroups(relatedGroups);
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
		ISText se = new ISText(50);

		Scanner scanner = new Scanner(System.in);

		System.out.print("Input: ");

		String line = null;
		while((line = scanner.nextLine()) != null){

			if (scanner.equals("exit exit")) break;

			String[] results = se.run(line, new LinkedList<Local>());
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
