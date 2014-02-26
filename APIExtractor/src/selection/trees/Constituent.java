package selection.trees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import selection.shares.IShare;

import edu.mit.jwi.item.POS;

public class Constituent {

	private int index;

	private IShare share;
	
	private List<Word> words;

	private POS pos;
	
	public Constituent(){
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

	public IShare getShare() {
		return share;
	}

	public void setShare(IShare share) {
		this.share = share;
	}
	
	public boolean contains(Word key){
		String keyLemma = key.getLemma();
		for(Word word: words){
			if (keyLemma.equals(word.getLemma())){
				return true;
			}
		}
		return false;	
	}
	
	public Word find(Word key){
		String keyLemma = key.getLemma();
		for(Word word: words){
			if (keyLemma.equals(word.getLemma())){
				return word;
			}
		}
		return null;
	}
}
