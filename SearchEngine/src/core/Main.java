package core;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import config.Config;
import deserializers.Deserializer;
import api.StabileAPI;
import search.ScoreListener;
import search.ScorerPipeline;
import search.Search;
import search.scorers.HitWeightScorer;
import search.scorers.RichDeclarationScorer;
import types.NameGenerator;
import nlp.parser.ComplexWordDecomposer;
import nlp.parser.Group;
import nlp.parser.IParser;
import nlp.parser.Input;
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

public class Main {
	public static void main(String[] args) {
		
		long time = System.currentTimeMillis();
		
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		StanfordCoreNLP coreNLP = new StanfordCoreNLP(props);

		System.out.println("Loading Stanford core time : "+(System.currentTimeMillis() - time)+" ms");
		time = System.currentTimeMillis();
		
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
		
		
		//Input input = pipeline.parse(new Input("open(file(\"text.txt\"), make)"));
		//Input input = pipeline.parse(new Input("open read close a file \"text.txt\""));

		//Input input = pipeline.parse(new Input("Open a buffered file 'text.txt', print it."));
		
		Input input = pipeline.parse(new Input("new InputStream(new Files(\"text.txt\"))"));
		
		System.out.println("Input parsing time : "+(System.currentTimeMillis() - time)+" ms");		
		time = System.currentTimeMillis();
		
		//Input input = pipeline.parse(new Input("Transfer contents of a file \"text1.txt\" to a file \"text2.txt\""));
		//Input input = pipeline.parse(new Input("Create a window."));
		//Input input = pipeline.parse(new Input("Read bytes from an input stream."));
		//Input input = pipeline.parse(new Input("Bell, based in Los Angeles, makes and distributes electronic, computer and building products."));
		// convert a String to an int
		// compare Strings in java
		//
		//Input input = pipeline.parse(new Input("Load a file \"text1.txt\" content into a buffer."));
	
		System.out.println(input);
		
		NameGenerator nameGen = new NameGenerator(Config.getDeserializerVariablePrefix());
		Deserializer deserializer = new Deserializer();
		StabileAPI api = new StabileAPI(deserializer.deserialize(Config.getSecondStorageLocation()), nameGen);	
		
		ScorerPipeline scorer = new ScorerPipeline(new RichDeclarationScorer[]{new HitWeightScorer()});
		ScoreListener listener = new ScoreListener(10);
		
		//Test matrix
		int[][] indexScoress = {{5, 3},{1, 2}};
		
		Search search = new Search(scorer, listener, api, indexScoress);
		
		System.out.println("Loading decls time : "+(System.currentTimeMillis() - time)+" ms");			
		time = System.currentTimeMillis();		
		
		List<Sentence> sentences = input.getSentences();
		
		for (Sentence sentence : sentences) {
			for (Group group : sentence.getSearchKeyGroups()) {
				search.search(group);	
			}	
			
			//TODO: Here we do synthesis, it is per sentence.
		}
		
		System.out.println("Search time : "+(System.currentTimeMillis() - time)+" ms");	
	}
}
