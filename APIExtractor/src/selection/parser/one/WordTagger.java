package selection.parser.one;

import java.io.IOException;

import selection.Config;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class WordTagger {
	private MaxentTagger tagger;

	public WordTagger(){
			this.tagger = new MaxentTagger(Config.getTaggerLocation());
	}
	
	public MaxentTagger getTagger() {
		return tagger;
	}
}