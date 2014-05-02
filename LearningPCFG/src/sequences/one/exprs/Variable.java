package sequences.one.exprs;

import java.util.LinkedList;
import java.util.List;

import selection.types.Type;
import util.Pair;

public class Variable extends Expr {
	private String value;
	private String name;

	public Variable(String name, String value, Type type) {
		super(type);
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "v("+name+", "+type+")";
	}

	@Override
	public String shortRep() {
		return value;
	}
	
	public List<Pair<String, String>> longReps(){
		return new LinkedList<Pair<String, String>>();
	}	

	@Override
	protected String representation() {
		return "";
	}
	
	@Override
	protected void representations(List<Pair<String, String>> list) {
			
	}
}
