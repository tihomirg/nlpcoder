package statistics.pretrees;

import java.util.List;

import statistics.Names;
import statistics.parsers.Result;
import statistics.parsers.SingleResult;
import types.Type;
import util.Pair;

public class Hole extends Expr{

	public Hole(){
		super(null);
	}
	
	public Hole(Type type) {
		super(type);
	}

	@Override
	public String toString() {
		return "hole("+type+")";
	}

	@Override
	public String shortRep() {
		return Names.Hole;
	}

	@Override
	protected String representation() {
		return "";
	}

	@Override
	protected void representations(List<Pair<String, String>> list) {
	}
	
	public static SingleResult parseShort(String string) {
		// TODO Auto-generated method stub
		return null;
	}	
}
