package statistics.pretrees;

import java.util.LinkedList;
import java.util.List;
import static statistics.parsers.Parser.*;
import statistics.Names;
import statistics.parsers.CompositeResult;
import statistics.parsers.Parser;
import statistics.parsers.SingleResult;
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
		return new Pair(shortRep(), shortRep()+Names.LPar+representation()+Names.RPar);
	}
	
	public List<Pair<String, String>> longReps(){
		List<Pair<String, String>> list = new LinkedList<Pair<String, String>>();
		list.add(longRep());
		representations(list);
		return list;
	}

	protected abstract void representations(List<Pair<String, String>> list);	
	
	protected abstract String representation();

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

	public static statistics.posttrees.Expr parse(String string) {
		SingleResult result = parseShort(string);
		statistics.posttrees.Expr expr = result.getExpr();
		expr.addArgs(parseArgs(result.getRest()).getExprs());
		return expr;
	}	
	
	public static CompositeResult parseArgs(String string){
		CompositeResult comp = new CompositeResult();
		if (!Parser.startsWithRPar(string)){
			SingleResult result = Parser.parseShort(string);
			String rest = result.getRest();
			comp.add(result.getExpr());
			while (!Parser.startsWithRPar(rest)) {
				result = Parser.parseShort(Parser.removeComma(rest));
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
