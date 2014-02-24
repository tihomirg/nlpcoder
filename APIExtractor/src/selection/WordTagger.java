package selection;

import java.io.IOException;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class WordTagger {
	private MaxentTagger tagger;

	public WordTagger(){
		try {
			this.tagger = new MaxentTagger(Config.getTaggerLocation());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public MaxentTagger getTagger() {
		return tagger;
	}
}