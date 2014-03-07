package selection.parser.one;

import java.io.Serializable;

import edu.mit.jwi.item.POS;

public class Word implements Cloneable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4177358907937741991L;
	
	private String lemma;
	private POS pos;
	private int index;
	private int constIndex;
	private double probability;
	
	public Word(){
		
	}
	
	public Word(String lemma, POS pos) {
		this(lemma, pos, 0);
	}
	
	public Word(String lemma, POS pos, int constIndex) {
		this(lemma, pos, constIndex, 0);
	}	
	
	public Word(String lemma, POS pos, int constIndex, int index) {
		this(lemma, pos, constIndex, 0, 0.0);
	}
	
	public Word(String lemma, POS pos, int constIndex, int index, double probability) {
		this.lemma = lemma;
		this.index = index;
		this.pos = pos;
		this.constIndex = constIndex;
		this.probability = probability;
	}

	public String getLemma() {
		return lemma;
	}
	public void setLemma(String lemma) {
		this.lemma = lemma;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public POS getPos() {
		return pos;
	}
	public void setPos(POS pos) {
		this.pos = pos;
	}

	public int getConstIndex() {
		return constIndex;
	}

	public void setConstIndex(int constIndex) {
		this.constIndex = constIndex;
	}	

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	@Override
	public Word clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (Word) super.clone();
	}
	
	@Override
	public String toString() {
		return "Word [lemma=" + lemma + ", pos=" + pos + ", index=" + index
				+ ", constIndex=" + constIndex + ", probability=" + probability
				+ "]";
	}
}
