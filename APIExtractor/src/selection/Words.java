package selection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import edu.mit.jwi.item.POS;

public class Words {

	private int index;

	private List<Word> words;

	private POS pos;
	
	public Words(){
		this.words = new ArrayList<Word>();
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void add(Word word) {
		words.add(word);
	}
	
	public void addAll(List<Word> words) {
		this.words.addAll(words);
	}	
	
	public void setPos(POS pos) {
		this.pos = pos;
	}
	
	public List<Word> getWords(){
		return words;
	}

	@Override
	public String toString() {
		return "Words [index=" + index + ", pos=" + pos+ ", words=" + Arrays.toString(words.toArray()) + "]\n";
	}
}
