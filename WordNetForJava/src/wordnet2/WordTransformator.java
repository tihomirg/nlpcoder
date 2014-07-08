package wordnet2;

import java.util.List;

import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.morph.WordnetStemmer;
import nlp.parser.TaggedWord;

public class WordTransformator {
	
	private WordnetStemmer stemmer;
	
	public WordTransformator(IDictionary dict) {
		this.stemmer = new WordnetStemmer(dict);
	}
	
	public TaggedWord getTransformStanfordTaggedWord(String word, String tag){
		String shortStanfordTag = getShortStanfordTag(tag);
		
		List<String> findStems = stemmer.findStems(word, fromShortStanfordTagToWordNetPos(shortStanfordTag));
		
		if (findStems != null && !findStems.isEmpty()) {
			String stem = findStems.get(0);
			return new TaggedWord(stem, shortStanfordTag);
		}
		else {
			return new TaggedWord(word, shortStanfordTag);
		}
	}
	
	public String getShortStanfordTag(String pos) {
		return Character.isLetter(pos.charAt(0)) ? Character.toString(pos.charAt(0)).toUpperCase() : pos;
	}

	public static POS fromShortStanfordTagToWordNetPos(String shortStanfordTag){
		if(shortStanfordTag.equals("N")){
			return POS.NOUN;
		} else if(shortStanfordTag.equals("V")){
			return POS.VERB;
		} else if(shortStanfordTag.equals("J")) {
			return POS.ADJECTIVE;
		} else if (shortStanfordTag.equals("R")){
			return POS.ADVERB;
		} else {
			return POS.NOUN;
		}
	}
}
