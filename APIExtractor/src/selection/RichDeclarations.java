package selection;

import java.util.HashSet;
import java.util.Set;

public class RichDeclarations {
	
	private Set<RichDeclaration> set = new HashSet<RichDeclaration>();
	
	public void add(RichDeclaration decl) {
		// TODO Auto-generated method stub
		set.add(decl);
	}

	public void inc(Word word) {
		for(RichDeclaration rd: set){
			rd.inc(word);
		}	
	}

}
