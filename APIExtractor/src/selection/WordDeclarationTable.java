package selection;

import selection.parser.one.Word;

public class WordDeclarationTable {
	private TagGroup[] groups;

	public WordDeclarationTable(int groupNum) {
		this.groups = new TagGroup[Config.getNumOfTags()];
		
		for(int i=0; i<groupNum; i++){
			this.groups[i] = new TagGroup();
		}
	}

	public void tryInc(Word word) {
		groups[word.getIndex()].tryInc(word);
	}

	public void addRichDeclaration(Word word, RichDeclaration rd) {
		groups[word.getIndex()].add(word.getLemma(), rd);
	}
}
