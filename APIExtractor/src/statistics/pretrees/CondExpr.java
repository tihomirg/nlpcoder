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

public class CondExpr extends Expr {

	private Expr exp;
	private Expr thenExp;
	private Expr elseExp;

	public CondExpr(Expr exp, Expr thenExp, Expr elseExp) {
		super(thenExp.getType());

		this.exp = exp;
		this.thenExp = thenExp;
		this.elseExp = elseExp;
	}

	public Expr getExp() {
		return exp;
	}

	public void setExp(Expr exp) {
		this.exp = exp;
	}

	public Expr getThenExp() {
		return thenExp;
	}

	public void setThenExp(Expr thenExp) {
		this.thenExp = thenExp;
	}

	public Expr getElseExp() {
		return elseExp;
	}

	public void setElseExp(Expr elseExp) {
		this.elseExp = elseExp;
	}

	@Override
	public String toString() {
		return exp + " ? " + thenExp + " : "+ elseExp;
	}

	@Override
	public String shortRep() {
		return Names.CondExpr+Names.LPar+type.getPrefix()+Names.RPar;
	}

	@Override
	protected String argReps() {
		return shortReps(exp, thenExp, elseExp);
	}
	
	@Override
	protected void longReps(List<Pair<String, String>> list) {
		list.addAll(exp.longReps());
		list.addAll(thenExp.longReps());
		list.addAll(elseExp.longReps());
	}

	public static SingleResult parseShort(String string, StabileTypeFactory tf) {
		String rest = removeLPar(removeCondExpr(string));
		StringResult result = parseStringTillRPar(rest);
		String typePrefix = result.getString();
		Type type = tf.createTypeByTypePrefix(typePrefix);
		rest = removeRPar(result.getRest());
		return new SingleResult(createCondExpr(type), rest);
	}

	@Override
	protected Declaration extractDecl() {
		return null;
	}

	@Override
	protected void extractDecls(List<Declaration> list) {
		list.addAll(exp.extractDecls());
		list.addAll(thenExp.extractDecls());
		list.addAll(elseExp.extractDecls());		
	}	
}
