package statistics.pretrees;

import java.util.List;

import statistics.Names;
import statistics.parsers.Result;
import statistics.parsers.SingleResult;
import types.Type;
import util.Pair;

public class CastExpr extends Expr {

	private Expr exp;

	public CastExpr(Type type, Expr exp) {
		super(type);
		this.exp = exp;
	}

	@Override
	public String toString() {
		return "("+type+")"+ exp;
	}

	@Override
	public String shortRep() {
		return Names.CastExpr+Names.LPar+type.getPrefix()+Names.RPar;
	}

	@Override
	protected String representation() {
		return exp.shortRep();
	}

	@Override
	protected void representations(List<Pair<String, String>> list) {
		list.addAll(exp.longReps());
	}

	public static SingleResult parseShort(String string) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
