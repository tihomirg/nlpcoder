package selection;

import java.util.List;

import selection.shares.IShare;
import selection.trees.Constituent;

public class Interval {

	private int wordIndex;
	
	private int firstIndex;
	private int lastIndex;
	
	private IShare share;
	private List<Constituent> constituents;
	
	public Interval(List<Constituent> constituents, int wordIndex, int firstIndex, int lastIndex, IShare share) {
		this.wordIndex = wordIndex;
		this.setFirstIndex(firstIndex);
		this.lastIndex = lastIndex;
		this.share = share;
		this.constituents = constituents;
	}
	
	public int getWordIndex() {
		return wordIndex;
	}
	
	public void setWordIndex(int wordIndex) {
		this.wordIndex = wordIndex;
	}
	
	public int getFirstIndex() {
		return firstIndex;
	}

	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	public int getLastIndex() {
		return lastIndex;
	}
	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}
	public IShare getShare() {
		return share;
	}
	public void setShare(IShare share) {
		this.share = share;
	}
	public List<Constituent> getWordss() {
		return constituents;
	}
	public void setWordss(List<Constituent> wordss) {
		this.constituents = wordss;
	}
}
