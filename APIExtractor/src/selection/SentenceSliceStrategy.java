package selection;

import java.util.ArrayList;
import java.util.List;
import edu.mit.jwi.item.POS;

public class SentenceSliceStrategy implements ISentenceSliceStrategy {

	private static final int DEFAULT_GROUP_INDEX = 0;
	private WordProcessor processor;

	public SentenceSliceStrategy(WordProcessor processor) {
		this.processor = processor;
	}
	
	@Override
	public List<Words> slice(String sentence) {
		List<Words> groups = new ArrayList<Words>();

		List<String> splits = tag(sentence);
		for(int i = 0; i < splits.size(); i++) {
			groups.add(getWords(TaggedLemma.create(splits.get(i)), i));
		}

		return groups;
	}

	private Words getWords(TaggedLemma subsentence, int wordIndex) {
		WordFactory factory = processor.getFactory();
		POS subsentencePos = subsentence.getTag();
		List<String> splits = tag(processor.decompose(subsentence.getWord()));
		
		List<Word> words = new ArrayList<Word>();
		for(int i = 0; i < splits.size(); i++){
			TaggedLemma lemma = TaggedLemma.create(splits.get(i));
			
			POS pos = lemma.getTag();
			String word = lemma.getWord();
			
			String name = processor.steam(word, pos);
			if (name != null) words.add(factory.getWord(name, pos, wordIndex, DEFAULT_GROUP_INDEX));
		}
		
		return factory.getWords(words, subsentencePos, wordIndex);
	}

	private List<String> tag(List<String> decomposedSentence) {
	  String s="";
	  for(String dec: decomposedSentence){
		  s+=dec+" ";
	  }
	  return tag(s);
    }

	private List<String> tag(String sentence) {
		String[] splits = tagSentence(sentence);
		List<String> list = new ArrayList<String>();
		
		for(String split: splits){
			System.out.println(split);
			
			if(!split.isEmpty()
				&& !split.equals("")
				&& Character.isJavaIdentifierPart(split.charAt(0))){
				list.add(split);
			}
		}
		
		return list;
	}

	public String[] tagSentence(String sentence){
		System.out.println(processor.getTagger().tagString(sentence));
		
		return processor.getTagger().tagString(sentence).split(" ");
	}
}
