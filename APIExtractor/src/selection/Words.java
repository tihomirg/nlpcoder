package selection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Words {

	private int index;
	
	private List<Word> words;
	
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

}
