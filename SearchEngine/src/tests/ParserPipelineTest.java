package tests;

import static org.junit.Assert.*;

import java.util.Properties;

import nlp.parser.RelatedWordsMap;
import nlp.parser.one.WordCorrector;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import search.config.SearchConfig;
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
import search.nlp.parser.WordPosCorrector;
import config.Config;
import deserializers.KryoDeserializer;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class ParserPipelineTest {
    
	private static ParserPipeline parser;

	/**
	 * 1) ParserForLiterals
	 * 2) ParserForLocals
	 * 3) ParserForCorrectingWords //We do not repair locals and literals
	 * 4) ParserForNaturalLanguage
	 * 5) ParserForRichLiteralsAndLocals
	 * 6) ParserForSemantincGraphRelations
	 * 7) ParserForRightHandSideNeighbours
	 * 8) ParserForComplexTokens
	 * 9) ParserForWTokens
	 * 10) ParserForIndexes
	 */
	@BeforeClass
	public static void before(){
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		props.put("pos.model", "edu/stanford/nlp/models/pos-tagger/english-bidirectional/english-bidirectional-distsim.tagger");
		//props.put("pos.model", "edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger");
		
		StanfordCoreNLP coreNLP = new StanfordCoreNLP(props);
		WordPosCorrector posCorrector = new WordPosCorrector();		
		
		ComplexWordDecomposer decomposer = new ComplexWordDecomposer(coreNLP, posCorrector);
		KryoDeserializer deserializer = new KryoDeserializer();
		RelatedWordsMap rwm = (RelatedWordsMap) deserializer.readObject(Config.getRelatedWordsMapLocation(), RelatedWordsMap.class);
		
		parser = new ParserPipeline(new IParser[]{
				new ParserForLiterals(),
				new ParserForLocals(),
				new ParserForCorrectingWords(new WordCorrector()),
				new ParserForNaturalLanguage(coreNLP, posCorrector),
				new ParserForRichLiteralsAndLocals(),
				new ParserForSemanticGraphNeighbours(),
				new ParserForRightHandSideNeighbours(1),
				new ParserForComplexTokens(decomposer),
				new ParserForWeightsAndImportanceIndexes(rwm, SearchConfig.getPrimaryIndex(), SearchConfig.getPrimaryWeight(), SearchConfig.getSecondaryIndex(), SearchConfig.getSecondaryWeight(), SearchConfig.getRelatedWeightFactor()),
				new ParserForDisjointSubgroups()});
	}
	
	@Test
	public void test1() {
		Input out = parser.parse(new Input("open new file \"text.txt\""));
	
		System.out.println(out);
	}
	
	@Test
	public void test2() {
		Input out = parser.parse(new Input("read file \"text.txt\""));
	
		System.out.println(out);
	}
	
	@Test
	public void test3() {
		Input out = parser.parse(new Input("fileReader \"text.txt\""));
	
		System.out.println(out);
	}
	
	@Test
	public void test4() {
		Input out = parser.parse(new Input("copy file \"text1.txt\" to \"text2.txt\""));
	
		System.out.println(out);
	}
	
}
