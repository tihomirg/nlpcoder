package selection;

import java.util.HashMap;
import java.util.Map;

public class Group {
	private Map<String, RichDeclarations> map = new HashMap<String, RichDeclarations>();
	
	public void add(String word, RichDeclaration decl) {
		if (!map.containsKey(word)){
			RichDeclarations decls = new RichDeclarations();
			decls.add(decl);
			map.put(word, decls);
		} else {
			RichDeclarations set = map.get(word);
			set.add(decl);
		}
	}

	public void tryInc(Word word) {
		RichDeclarations rd = map.get(word.getLemma());
		if (rd != null) rd.inc(word);
	}
}
