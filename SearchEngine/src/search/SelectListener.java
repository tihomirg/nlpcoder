package search;

import java.util.LinkedList;
import java.util.List;

public class SelectListener {

	private List<DeclarationSelectionEntry> selected;

	public SelectListener() {
		this.selected = new LinkedList<DeclarationSelectionEntry>();
	}

	public void notify(DeclarationSelectionEntry rd) {
		selected.add(rd);
	}

	public void clear(){
		for (DeclarationSelectionEntry rd: this.selected) {
			rd.clear();
		}
		selected.clear();
	}

	public List<DeclarationSelectionEntry> getSelected() {
		return selected;
	}
}
