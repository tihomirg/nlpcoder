package selection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.mit.jwi.item.POS;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class SentenceSliceStrategy implements ISentenceSliceStrategy {

	private WordProcessor processor;
	private WordFactory factory;
	private MaxentTagger tagger;

	public SentenceSliceStrategy(WordProcessor processor) {
		this.processor = processor;
		try {
			this.tagger = new MaxentTagger("C:/Users/gvero/git/lib/stanford-postagger-2011-04-20/models/left3words-wsj-0-18.tagger");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Words> slice(String sentence) {
		List<Words> groups = new ArrayList<Words>();

		String[] splits = trim(sentence);

		for(int i = 0; i< splits.length; i++) {
			String split = splits[i];
			TaggedString tagged = TaggedString.create(split);

			groups.add(getWords(tagged, i));
		}

		return groups;
	}

	private Words getWords(TaggedString tagged, int i) {
		return factory.getWords(processor.sliceComplexWord(tagged.getWord()), tagged.getTag(), i, 0);
	}

	private String[] trim(String sentence) {
		String[] splits = tagSentence(sentence);

		if (splits[splits.length-1].equals(".")){
			splits = Arrays.copyOf(splits, splits.length-1);
		}
		return splits;
	}

	public String[] tagSentence(String sentence){
		return tagger.tagString(sentence).split(" ");
	}
}
