package selection;

import java.util.HashMap;
import java.util.Map;

import definitions.Declaration;

public class RichDeclaration {

	private Declaration decl;
	private double initVal;

	private Map<Integer, Double> indexToVal;
	
	public RichDeclaration(Declaration decl){
		this(decl, 0.0);
	}
	
	public RichDeclaration(Declaration decl, double initVal) {
		this.decl = decl;
		this.initVal = initVal;
		this.indexToVal = new HashMap<Integer, Double>();
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
	
	public void inc(Word word) {
		if(!indexToVal.containsKey(word.getWordIndex())){
			indexToVal.put(word.getWordIndex(), word.getValue());
		} else {
			double val = indexToVal.get(word.getWordIndex());
			indexToVal.put(word.getWordIndex(), val + word.getValue());
		}
	}
	
	public void clear(){
		indexToVal.clear();
	}

}
