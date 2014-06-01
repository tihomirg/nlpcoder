package core;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import parser.ComplexWordDecomposer;
import parser.IParser;
import parser.Input;
import parser.ParserExtractLiterals;
import parser.ParserIdentifyLocals;
import parser.ParserNLP;
import parser.ParserPipeline;
import parser.ParserGroupsAndDependencyRelations;
import parser.ParserRelatedWords;
import parser.ParserRightHandSideNeighbours;
import parser.ParserSliceComplexTokens;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class Main {
	public static void main(String[] args) {
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
						new ParserRightHandSideNeighbours(2),
						new ParserSliceComplexTokens(decomposer),						
						new ParserRelatedWords()});
		
		
		//Input input = pipeline.parse(new Input("open(file(\"text.txt\"), make)"));
		//Input input = pipeline.parse(new Input("open read close a file \"text.txt\""));

		//Input input = pipeline.parse(new Input("Open a buffered file 'text.txt', print it."));
		Input input = pipeline.parse(new Input("new InputStream(new File(\"text.txt\"))"));
		//Input input = pipeline.parse(new Input("Transfer contents of a file \"text1.txt\" to a file \"text2.txt\""));
		//Input input = pipeline.parse(new Input("Create a window."));
		//Input input = pipeline.parse(new Input("Read bytes from an input stream."));
		//Input input = pipeline.parse(new Input("Bell, based in Los Angeles, makes and distributes electronic, computer and building products."));


		// convert a String to an int
		// compare Strings in java
		//
		//Input input = pipeline.parse(new Input("Load a file \"text1.txt\" content into a buffer."));

		System.out.println(input);
	}
}
