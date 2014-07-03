package nlp.parser.one;

import java.io.IOException;

import config.Config;


import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class WordTagger {
	private MaxentTagger tagger;

	public WordTagger(){
			this.tagger = new MaxentTagger(Config.getTaggerLocation());
	}
	
	public MaxentTagger getTagger() {
		return tagger;
	}
	
	public String[] tagSentence(String sentence){
		return tagger.tagString(sentence).split(" ");
	}
}