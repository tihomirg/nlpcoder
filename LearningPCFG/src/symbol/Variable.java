package symbol;

import java.util.Set;

import selection.types.Type;
import definitions.Declaration;

public class Variable extends Symbol {
	
	private String name;
	
	public Variable(String name) {
		this.name = name;
	}
 
	@Override
	public String head() {
		return "Variable("+name+")";
	}
	
	@Override
	public boolean isVariable(){
		return true;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Variable("+name+")";
	}

	@Override
	public Type retType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasRetType() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<Declaration> getDecls() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasDecls() {
		// TODO Auto-generated method stub
		return false;
	}
}
