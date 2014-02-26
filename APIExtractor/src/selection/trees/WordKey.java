package selection.trees;

import selection.shares.IShare;
import edu.mit.jwi.item.POS;

public class WordKey {

	private String lemma;
	private int groupIndex;
	private IShare share;
	private POS pos;

	public WordKey(String lemma, POS pos){
		this(lemma, pos, null);
	}	
	
	public WordKey(String lemma, POS pos, IShare share){
		this(lemma, pos, share, 0);
	}
	
	public WordKey(String lemma, POS pos, IShare share, int groupIndex) {
		this.lemma = lemma;
		this.share = share;
		this.pos = pos;
		this.groupIndex = groupIndex;		
	}	
	
	public String getLemma() {
		return lemma;
	}
	public void setLemma(String lemma) {
		this.lemma = lemma;
	}
	public int getGroupIndex() {
		return groupIndex;
	}
	public void setGroupIndex(int groupIndex) {
		this.groupIndex = groupIndex;
	}
	
	public POS getPos() {
		return pos;
	}

	public double getProbability() {
		return share.getProbability();
	}

	public IShare getShare() {
		return share;
	}

	public void setShare(IShare share) {
		this.share = share;
	}
}
