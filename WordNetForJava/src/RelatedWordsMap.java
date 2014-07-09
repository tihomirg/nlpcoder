import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import wordnet2.TaggedWordMeaning;
import nlp.parser.TaggedWord;

public class RelatedWordsMap {

	private Map<TaggedWord, List<TaggedWordMeaning>> map;
	
	public RelatedWordsMap() {
		this.map = new HashMap<TaggedWord, List<TaggedWordMeaning>>();
	}

	public void put(TaggedWord leftHSWord, List<TaggedWordMeaning> list) {
		this.map.put(leftHSWord, list);
	}
	
	public List<TaggedWordMeaning> get(TaggedWord leftHSWord){
		return this.map.get(leftHSWord);
	}
}
