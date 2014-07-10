package search.nlp.parser2;

import static org.junit.Assert.*;

import java.util.Properties;

import nlp.parser.one.WordCorrector;

import org.junit.Before;
import org.junit.Test;

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
	 * 9) ParserForRelatedWords
	 * 10) ParserForIndexes
	 * 11) ParserForScores
	 */
	@Before
	public void before(){
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		StanfordCoreNLP coreNLP = new StanfordCoreNLP(props);

		ComplexWordDecomposer decomposer = new ComplexWordDecomposer(coreNLP);
		
		parser = new ParserPipeline(new IParser[]{
				new ParserForLiterals(),
				new ParserForLocals(),
				new ParserForCorrectingWords(new WordCorrector()),
				new ParserForNaturalLanguage(coreNLP),
				new ParserForRichLiteralsAndLocals(),
				new ParserForSemanticGraphNeighbours(),
				new ParserForRightHandSideNeighbours(1),
				new ParserForComplexTokens(decomposer)});
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
	
}
