package core;

import java.util.Properties;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class Main {
	public static void main(String[] args) {
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		StanfordCoreNLP coreNLP = new StanfordCoreNLP(props);
		
		
	}
}
