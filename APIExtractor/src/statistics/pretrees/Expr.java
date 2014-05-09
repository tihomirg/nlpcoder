package statistics.pretrees;

import java.util.LinkedList;
import java.util.List;
import static statistics.parsers.Parser.*;
import statistics.Names;
import statistics.parsers.CompositeResult;
import statistics.parsers.Parser;
import statistics.parsers.SingleResult;
import types.StabileTypeFactory;
import types.Type;
import util.Pair;

public abstract class Expr {
	protected Type type;
	
	public Expr(Type type) {
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	public abstract String shortRep();
	
	public Pair<String, String> longRep(){
		return new Pair(shortRep(), shortRep()+Names.LPar+argReps()+Names.RPar);
	}
	
	public List<Pair<String, String>> longReps(){
		List<Pair<String, String>> list = new LinkedList<Pair<String, String>>();
		list.add(longRep());
		longReps(list);
		return list;
	}

	protected abstract void longReps(List<Pair<String, String>> list);	
	
	protected abstract String argReps();

	public static String shortReps(Expr... args){
		String s = "";
		if (args.length > 0){
			s += args[0].shortRep();
			for (int i = 1; i < args.length; i++) {
				s+=Names.Comma+args[i].shortRep();
			}
		}
		return s;
	}

	public static statistics.posttrees.Expr parse(String string, StabileTypeFactory tf) {
		SingleResult result = parseShort(string, tf);
		statistics.posttrees.Expr expr = result.getExpr();
		expr.addArgs(parseArgs(result.getRest(), tf).getExprs());
		return expr;
	}
	
	public static CompositeResult parseArgs(String string, StabileTypeFactory tf){
		CompositeResult comp = new CompositeResult();
		if (!Parser.startsWithRPar(string)){
			SingleResult result = Parser.parseShort(string, tf);
			String rest = result.getRest();
			comp.add(result.getExpr());
			while (!Parser.startsWithRPar(rest)) {
				result = Parser.parseShort(Parser.removeComma(rest), tf);
				comp.add(result.getExpr());
				rest = result.getRest();
			}
		}
		return comp;
	}

	public boolean isVariable() {
		return false;
	}
}
