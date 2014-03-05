package selection;

import java.util.HashMap;
import java.util.Map;

import selection.parser.one.Word;

import definitions.Declaration;

public class RichDeclaration {

	private Declaration decl;	
	private double initVal;
	private Map<Integer, Double> probabilities;

	private Indexes indexes;

	public RichDeclaration(Declaration decl, Indexes indexes){
		this(decl, 0.0, indexes);
	}

	public RichDeclaration(Declaration decl, double initVal, Indexes indexes) {
		this.decl = decl;
		this.initVal = initVal;
		this.indexes = indexes;
		this.probabilities = new HashMap<Integer, Double>();
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

	public void inc(Word key, TopList top){
		top.put(this, incMap(key.getConstIndex(), indexes.getProbability(key)));
	}

	private double incMap(int index, double addProb) {
		double prob;
		if(!probabilities.containsKey(index)){
			probabilities.put(index, prob = addProb);
		} else {
			double val = probabilities.get(index);
			probabilities.put(index, prob = (val + addProb));
		}
		return prob;
	}

	public void clear(){
		probabilities.clear();
	}

	@Override
	public String toString() {
		return decl.toString();
	}

}
