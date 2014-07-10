package nlp.parser;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
