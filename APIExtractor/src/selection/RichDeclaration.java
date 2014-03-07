package selection;

import java.util.HashMap;
import java.util.Map;

import selection.parser.one.Word;

import definitions.Declaration;

public class RichDeclaration {

	private Declaration decl;	
	private double initVal;
	//private Map<Integer, Double> probabilities;

	private RichProbability probabilities;
	
	private Indexes indexes;

	public RichDeclaration(Declaration decl, Indexes indexes){
		this(decl, 0.0, indexes);
	}

	public RichDeclaration(Declaration decl, double initVal, Indexes indexes) {
		this.decl = decl;
		this.initVal = initVal;
		this.indexes = indexes;
		this.probabilities = new RichProbability();
	}

	public Declaration getDecl() {
		return decl;
	}

	public void setDecl(Declaration decl) {
		this.decl = decl;
	}

	public double getInitVal() {
		return initVal;
	}

	public void setInitVal(double initVal) {
		this.initVal = initVal;
	}	

	public void inc(Word key, TopList top, int consLength){
		double prob = incMap(key.getConstIndex(), key.getIndex(), indexes.getProbability(key, consLength));
		if (prob != -1){
		  top.put(this, prob);		  
		}
	}

	private double incMap(int consIndex, int wordIndex, double addProb) {
		return probabilities.inc(consIndex, wordIndex, addProb);
	}

	public void clear(){
		probabilities.clear();
	}

	@Override
	public String toString() {
		return decl.toString()+" "+this.probabilities.getStat();
	}

}
