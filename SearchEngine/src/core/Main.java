package core;

import java.util.Properties;

import parser.IParser;
import parser.Input;
import parser.ParserExtractLiterals;
import parser.ParserNLP;
import parser.ParserPipeline;
import parser.ParserDependencyRelations;
import parser.ParserRightHandSideNeighbours;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class Main {
	public static void main(String[] args) {
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		StanfordCoreNLP coreNLP = new StanfordCoreNLP(props);
		
		ParserPipeline pipeline = new ParserPipeline(new IParser[]{new ParserExtractLiterals(), new ParserNLP(coreNLP), new ParserDependencyRelations(), new ParserRightHandSideNeighbours(2)});
		//Input input = pipeline.parse(new Input("open(file(\"text.txt\"), make)"));
		//Input input = pipeline.parse(new Input("open read close a file \"text.txt\""));
		
		//Input input = pipeline.parse(new Input("Open a buffered file 'text.txt', print it."));
		//Input input = pipeline.parse(new Input("Transfer contents of a file \"text1.txt\" to a file \"text2.txt\""));
		//Input input = pipeline.parse(new Input("Create a window."));
		//Input input = pipeline.parse(new Input("Read bytes from an input stream."));
		//Input input = pipeline.parse(new Input("Bell, based in Los Angeles, makes and distributes electronic, computer and building products."));
		
		
		// convert a String to an int
		// compare Strings in java
		//
		Input input = pipeline.parse(new Input("Load a file \"text1.txt\" content into a buffer."));
		
		System.out.println(input);
	}
}
