package selection;

import selection.parser.one.Word;
import selection.scorers.Scorer;

import definitions.Declaration;

public class RichDeclaration {

	private Declaration decl;	
	private Scorer scorer;

	public RichDeclaration(Declaration decl, Scorer incrementer) {
		this.decl = decl;
		this.scorer = incrementer;
	}

	public Declaration getDecl() {
		return decl;
	}

	public void setDecl(Declaration decl) {
		this.decl = decl;
	}	

	public void inc(Word key, TopList top, int consLength){
		top.tryPut(this, scorer.getScore(key));
	}
	
	public void clear(){
		scorer.clear();
	}

	@Override
	public String toString() {
		return scorer +"  "+ decl.toString();
	}

	public String toString(int constIndex) {
		//return scorer.toString(constIndex) +"  "+ decl.toString();
		return " "+ decl.toString();
	}
}
