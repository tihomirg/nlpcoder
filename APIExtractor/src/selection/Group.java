package selection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import definitions.Declaration;

import selection.parser.one.Word;

public class Group {
	private Map<String, RichDeclarations> map = new HashMap<String, RichDeclarations>();
	
	private Set<RichDeclarations> changed = new HashSet<RichDeclarations>();
	
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
		if (rd != null) {
			rd.inc(word);
			changed.add(rd);
		}
	}
	
	public void clear(){
		for (RichDeclarations rds : changed) {
			rds.clear();
		}
		changed.clear();
	}

	public RichDeclarations select(Word word) {
		return map.get(word.getLemma());
	}
}
