package selection;

import java.util.LinkedList;
import java.util.List;

import selection.parser.one.Word;
import selection.parser.one.trees.SentenceOne;

public class DeclarationSentenceOne implements IDeclarationSentence {

	private List<SentenceOne> result;

	public DeclarationSentenceOne(List<SentenceOne> result) {
		this.result = result;
	}

	public List<SentenceOne> getResult() {
		return result;
	}

	public void setResult(List<SentenceOne> result) {
		this.result = result;
	}

	@Override
	public IDeclarationSentence apply(IDeclarationParser iDeclarationParser) {
		return iDeclarationParser.parse(this);
	}

	public Word[] getWords() {
		
		List<Word> words = new LinkedList<Word>();
		for (SentenceOne sentence : result) {
			Word[] words2 = sentence.getWords();
			for (Word word : words2) {
				words.add(word);
			}
		}
		
		return words.toArray(new Word[words.size()]);
	}
}
