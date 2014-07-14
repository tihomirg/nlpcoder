package search.nlp.parser2;

import static org.junit.Assert.*;

import java.util.Properties;

import nlp.parser.RelatedWordsMap;
import nlp.parser.one.WordCorrector;

import org.junit.Before;
import org.junit.Test;

import config.Config;
import deserializers.KryoDeserializer;
import search.nlp.parser.ComplexWordDecomposer;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class ParserPipelineTest {
    
	private ParserPipeline parser;

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
	@Before
	public void before(){
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		props.put("pos.model", "edu/stanford/nlp/models/pos-tagger/english-bidirectional/english-bidirectional-distsim.tagger");
		//props.put("pos.model", "edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger");
		
		StanfordCoreNLP coreNLP = new StanfordCoreNLP(props);
		ComplexWordDecomposer decomposer = new ComplexWordDecomposer(coreNLP);
		KryoDeserializer deserializer = new KryoDeserializer();
		RelatedWordsMap rwm = (RelatedWordsMap) deserializer.readObject(Config.getRelatedWordsMapLocation(), RelatedWordsMap.class);
		
		parser = new ParserPipeline(new IParser[]{
				new ParserForLiterals(),
				new ParserForLocals(),
				new ParserForCorrectingWords(new WordCorrector()),
				new ParserForNaturalLanguage(coreNLP),
				new ParserForRichLiteralsAndLocals(),
				new ParserForSemanticGraphNeighbours(),
				new ParserForRightHandSideNeighbours(1),
				new ParserForComplexTokens(decomposer),
				new ParserForWTokens(rwm, 1.0, 0.5, 1.0),
				new ParserForIndexes()});
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
