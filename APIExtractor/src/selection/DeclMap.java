package selection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import definitions.Declaration;

public class DeclMap {

	private Map<Integer, Declaration> decls;
	
	public DeclMap() {
		this.decls = new HashMap<Integer, Declaration>();
	}

	public void add(Set<Declaration> decls){
		for (Declaration decl : decls) {
			add(decl);
		}
	}
	
	public void add(Declaration decl) {
		decls.put(decl.getId(), decl);
	}
	
	public Declaration getDecl(int id){
		return decls.get(id);
	}

}
