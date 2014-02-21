package selection;

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

	public void addRichDeclaration(WordIndex wordIndex, RichDeclaration rd) {
		groups[wordIndex.getGroupIndex()].add(wordIndex.getLemma(), rd);
	}
}
