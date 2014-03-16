package selection.types;

import java.util.HashMap;
import java.util.Map;

public class TypeFactory {
	
	private final Map<Integer, Polymorphic> poly = new HashMap<Integer, Polymorphic>();
	private final Map<String, Const> consts = new HashMap<String, Const>();
	private final Map<String, Variable> vars = new HashMap<String, Variable>(); 
	
	public Variable createVariable(String name) {
		if(!vars.containsKey(name)){
			vars.put(name, new Variable(name));
		}
		return vars.get(name);
	}
	
	public Const createConst(String name) {
		if(!consts.containsKey(name)){
			consts.put(name, new Const(name));
		}
		return consts.get(name);	
	}
	
	public Polymorphic createPolymorphic(String name, Type[] params) {
		int hash = Polymorphic.getHashCode(name, params);
		if (!poly.containsKey(hash)) {
			poly.put(hash, new Polymorphic(name, params));
		}
		return poly.get(hash);
	}

	@Override
	public String toString() {
		return "TypeFactory [\npoly=" + poly.values() + ", \nconsts=" + consts.values() + ", \nvars="
				+ vars.values() + "]";
	}
}
