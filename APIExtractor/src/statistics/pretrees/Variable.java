package statistics.pretrees;

import java.util.LinkedList;
import java.util.List;
import types.Type;
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
	protected String argReps() {
		return "";
	}
	
	@Override
	protected void longReps(List<Pair<String, String>> list) {
			
	}
	
	@Override
	public boolean isVariable() {
		return true;
	}	
}
