package selection;

import selection.trees.Word;

public class WordDeclarationTable {
	private Group[] groups;

	public WordDeclarationTable(int groupNum) {
		this.groups = new Group[groupNum];
		
		for(int i=0; i<groupNum; i++){
			this.groups[i] = new Group();
		}
	}

	public void tryInc(Word word) {
		groups[word.getGroupIndex()].tryInc(word);
	}

	public void addRichDeclaration(Word word, RichDeclaration rd) {
		groups[word.getGroupIndex()].add(word.getLemma(), rd);
	}
}
