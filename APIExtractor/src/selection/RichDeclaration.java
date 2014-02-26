package selection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import selection.trees.Constituent;
import selection.trees.Word;

import definitions.Declaration;

public class RichDeclaration {

	private Declaration decl;	
	private double initVal;
	private Map<Integer, Double> indexToVal;

	//We assume that each lemma appears exactly once in all words!
	//Should be 
	private List<Constituent> words;

	public RichDeclaration(Declaration decl, List<List<Word>> wordsList){
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

	public void inc(Word key){
		Word word = find(key);
		if (word != null){
			incMap(key.getWordIndex(), calcWeight(key, word));
		}
	}

	private double calcWeight(Word key, Word word) {
		// TODO Include word number!
		return key.getProbability() * word.getProbability();
	}

	private Word find(Word key){
		for(Constituent words1: words){
			Word word = words1.find(key);
			if (word != null){
				return word;
			}
		}
		return null;
	}

	private void incMap(int index, double addProb) {
		if(!indexToVal.containsKey(index)){
			indexToVal.put(index, addProb);
		} else {
			double val = indexToVal.get(index);
			indexToVal.put(index, val + addProb);
		}
	}

	public void clear(){
		indexToVal.clear();
	}

	public List<Constituent> getWords() {
		return words;
	}

	public void setWords(List<Constituent> words) {
		this.words = words;
	}

}
