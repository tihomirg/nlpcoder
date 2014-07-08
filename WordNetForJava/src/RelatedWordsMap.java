import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import nlp.parser.TaggedWord;

public class RelatedWordsMap {

	private Map<TaggedWord, Set<TaggedWord>> map;
	
	public RelatedWordsMap() {
		this.map = new HashMap<TaggedWord, Set<TaggedWord>>();
	}

	public void put(TaggedWord leftHSWord, Set<TaggedWord> rightHSWords) {
		this.map.put(leftHSWord, rightHSWords);
	}

}
