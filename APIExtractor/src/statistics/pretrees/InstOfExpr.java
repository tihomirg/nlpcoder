package statistics.pretrees;

import static statistics.parsers.Parser.*;

import java.util.List;

import definitions.Declaration;
import statistics.Names;
import statistics.parsers.Parser;
import statistics.parsers.SingleResult;
import statistics.parsers.StringResult;
import types.StabileTypeFactory;
import types.Type;
import util.Pair;

public class InstOfExpr extends Expr {

	private Expr exp;
	private Type checkType;

	public InstOfExpr(Expr exp, Type checkType, Type type) {
		super(type);
		this.exp = exp;
		this.checkType = checkType;
	}

	public Expr getExp() {
		return exp;
	}

	public void setExp(Expr exp) {
		this.exp = exp;
	}

	@Override
	public String toString() {
		return exp +" instanceof "+checkType;
	}

	@Override
	public String shortRep() {
		return Names.InstOfExpr+Names.LPar+checkType.getPrefix()+Names.RPar;
	}

	@Override
	protected String argReps() {
		return shortReps(exp);
	}
	
	@Override
	protected void longReps(List<Pair<String, String>> list) {
		list.addAll(exp.longReps());
	}

	public static SingleResult parseShort(String string, StabileTypeFactory tf) {
		String rest = removeLPar(removeInstOfExpr(string));
		StringResult result = parseStringTillRPar(rest);
		String typePrefix = result.getString();
		Type type = tf.createTypeByTypePrefix(typePrefix);
		rest = removeRPar(result.getRest());
		return new SingleResult(Parser.createInstOfExpr(type), rest);
	}

	@Override
	protected Declaration extractDecl() {
		return null;
	}

	@Override
	protected void extractDecls(List<Declaration> list) {
		list.addAll(exp.extractDecls());		
	}	
}
