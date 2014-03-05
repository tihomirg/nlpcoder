package selection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import definitions.Declaration;

import edu.mit.jwi.item.POS;

import selection.parser.one.Word;

public class Table {
	private Map<POS,Group> groups;
	
	public Table(int groupNum) {
		this.groups = new HashMap<POS, Group>();
		this.groups.put(POS.NOUN, new Group());		
		this.groups.put(POS.VERB, new Group());		
		this.groups.put(POS.ADVERB, new Group());		
		this.groups.put(POS.ADJECTIVE, new Group());
		this.groups.put(null, new Group());
	}

	public void tryInc(Word word, TopList top) {
		groups.get(word.getPos()).tryInc(word, top);
	}

	public void addRichDeclaration(Word word, RichDeclaration rd) {
		groups.get(word.getPos()).add(word.getLemma(), rd);
	}

	public RichDeclarations select(Word word) {
		return groups.get(word.getPos()).select(word);
	}
	
	public void clear() {
		for (Group group : groups.values()) {
			group.clear();
		}
	}
}
