package nlp.parser.declarations;

import java.util.LinkedList;
import java.util.List;

import nlp.parser.one.Word;
import nlp.parser.one.trees.SentenceOne;


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
		
		List<Word> newWords = new LinkedList<Word>();
		int size = result.size();
		for (int i = 0; i < size; i++){
			SentenceOne sentence = result.get(i);
			Word[] words = sentence.getWords();
			for (Word word : words) {
				word.setGroup(i);
				newWords.add(word);
			}
		}
		
		return newWords.toArray(new Word[newWords.size()]);
	}
}
