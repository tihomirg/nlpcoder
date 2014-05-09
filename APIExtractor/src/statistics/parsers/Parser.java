package statistics.parsers;

import statistics.Names;
import statistics.posttrees.Expr;
import statistics.posttrees.StringExpr;
import statistics.pretrees.Assignment;
import statistics.pretrees.BooleanLiteral;
import statistics.pretrees.CastExpr;
import statistics.pretrees.CharacterLiteral;
import statistics.pretrees.CondExpr;
import statistics.pretrees.ConstructorInvocation;
import statistics.pretrees.Hole;
import statistics.pretrees.InfixOperator;
import statistics.pretrees.InstOfExpr;
import statistics.pretrees.InstanceFieldAccess;
import statistics.pretrees.InstanceMethodInvocation;
import statistics.pretrees.NullLiteral;
import statistics.pretrees.NumberLiteral;
import statistics.pretrees.PostfixOperator;
import statistics.pretrees.PrefixOperator;
import statistics.pretrees.StringLiteral;

public class Parser {
	public static Expr parse(String string){
		if (startsWithAssignment(string)){
			return Assignment.parse(string);
		} else if (startsWithBooleanLiteral(string)) {
			return BooleanLiteral.parse(string);
		} else if (startsWithCastExpr(string)) {
			return CastExpr.parse(string);
		} else if (startsWithCharacterLiteral(string)) {
			return CharacterLiteral.parse(string);
		} else if (startsWithCondExpr(string)) {
			return CondExpr.parse(string);
		} else if (startsWithConstructorInvocation(string)) {
			return ConstructorInvocation.parse(string);
		} else if (startsWithHole(string)) {
			return Hole.parse(string);
		} else if (startsWithInfixOperator(string)) {
			return InfixOperator.parse(string);
		} else if (startsWithInstanceFieldAccess(string)) {
			return InstanceFieldAccess.parse(string);
		} else if (startsWithInstanceMethodInvocation(string)) {
			return InstanceMethodInvocation.parse(string);
		} else if (startsWithInstOfExpr(string)) {
			return InstOfExpr.parse(string);
		} else if (startsWithNullLiteral(string)) {
			return NullLiteral.parse(string);
		} else if (startsWithNumberLiteral(string)) {
			return NumberLiteral.parse(string);
		} else if (startsWithPostfixOperator(string)) {
			return PostfixOperator.parse(string);
		} else if (startsWithPrefixOperator(string)) {
			return PrefixOperator.parse(string);
		} else {//it is startsWithStringLiteral(string)
			return StringLiteral.parse(string);
		}
	}

	public static boolean startsWithAssignment(String string){
		return string.startsWith(Names.Assignment);
	}
	
	public static boolean startsWithBooleanLiteral(String string){
		return string.startsWith(Names.BooleanLiteral);
	}	
	
	public static boolean startsWithCastExpr(String string){
		return string.startsWith(Names.CastExpr);
	}
	
	public static boolean startsWithCharacterLiteral(String string){
		return string.startsWith(Names.CharacterLiteral);
	}
	
	public static boolean startsWithCondExpr(String string){
		return string.startsWith(Names.CondExpr);
	}	
	
	public static boolean startsWithConstructorInvocation(String string){
		return string.startsWith(Names.ConstructorInvocation);
	}	
	
	public static boolean startsWithHole(String string){
		return string.startsWith(Names.Hole);
	}	
	
	public static boolean startsWithInfixOperator(String string){
		return string.startsWith(Names.InfixOperator);
	}
	
	public static boolean startsWithInstanceFieldAccess(String string){
		return string.startsWith(Names.InstanceFieldAccess);
	}
	
	public static boolean startsWithInstanceMethodInvocation(String string){
		return string.startsWith(Names.InstanceMethodInvocation);
	}
	
	public static boolean startsWithInstOfExpr(String string){
		return string.startsWith(Names.InstOfExpr);
	}	
	
	public static boolean startsWithNullLiteral(String string){
		return string.startsWith(Names.NullLiteral);
	}
	
	public static boolean startsWithNumberLiteral(String string){
		return string.startsWith(Names.NumberLiteral);
	}	
	
	public static boolean startsWithPostfixOperator(String string){
		return string.startsWith(Names.PostfixOperator);
	}
	
	public static boolean startsWithPrefixOperator(String string){
		return string.startsWith(Names.PrefixOperator);
	}
	
	public static boolean startsWithStringLiteral(String string){
		return string.startsWith(Names.StringLiteral);
	}
	
	public static String removeAssignment(String string){
		return string.substring(Names.Assignment.length());
	}
	
	public static String removeBooleanLiteral(String string){
		return string.substring(Names.BooleanLiteral.length());
	}	
	
	public static String removeCastExpr(String string){
		return string.substring(Names.CastExpr.length());
	}
	
	public static String removeCharacterLiteral(String string){
		return string.substring(Names.CharacterLiteral.length());
	}
	
	public static String removeCondExpr(String string){
		return string.substring(Names.CondExpr.length());
	}	
	
	public static String removeConstructorInvocation(String string){
		return string.substring(Names.ConstructorInvocation.length());
	}	
	
	public static String removeHole(String string){
		return string.substring(Names.Hole.length());
	}	
	
	public static String removeInfixOperator(String string){
		return string.substring(Names.InfixOperator.length());
	}
	
	public static String removeInstanceFieldAccess(String string){
		return string.substring(Names.InstanceFieldAccess.length());
	}
	
	public static String removeInstanceMethodInvocation(String string){
		return string.substring(Names.InstanceMethodInvocation.length());
	}
	
	public static String removeInstOfExpr(String string){
		return string.substring(Names.InstOfExpr.length());
	}	
	
	public static String removeNullLiteral(String string){
		return string.substring(Names.NullLiteral.length());
	}
	
	public static String removeNumberLiteral(String string){
		return string.substring(Names.NumberLiteral.length());
	}	
	
	public static String removePostfixOperator(String string){
		return string.substring(Names.PostfixOperator.length());
	}
	
	public static String removePrefixOperator(String string){
		return string.substring(Names.PrefixOperator.length());
	}
	
	public static String removeStringLiteral(String string){
		return string.substring(Names.StringLiteral.length());
	}
	
	public static String removeLPar(String string){
		return string.substring(Names.LPar.length());
	}
	
	public static String removeRPar(String string){
		return string.substring(Names.RPar.length());
	}
	
	public static String removeComma(String string){
		return string.substring(Names.Comma.length());
	}	

	public static StringResult parseStringTillRPar(String string){
		String op = string.substring(0, string.indexOf(Names.RPar));
		return new StringResult(op, string.substring(op.length()));
	}

	public static SingleResult parseShort(String string) {
		if (startsWithAssignment(string)){
			return Assignment.parseShort(string);
		} else if (startsWithBooleanLiteral(string)) {
			return BooleanLiteral.parseShort(string);
		} else if (startsWithCastExpr(string)) {
			return CastExpr.parseShort(string);
		} else if (startsWithCharacterLiteral(string)) {
			return CharacterLiteral.parseShort(string);
		} else if (startsWithCondExpr(string)) {
			return CondExpr.parseShort(string);
		} else if (startsWithConstructorInvocation(string)) {
			return ConstructorInvocation.parseShort(string);
		} else if (startsWithHole(string)) {
			return Hole.parseShort(string);
		} else if (startsWithInfixOperator(string)) {
			return InfixOperator.parseShort(string);
		} else if (startsWithInstanceFieldAccess(string)) {
			return InstanceFieldAccess.parseShort(string);
		} else if (startsWithInstanceMethodInvocation(string)) {
			return InstanceMethodInvocation.parseShort(string);
		} else if (startsWithInstOfExpr(string)) {
			return InstOfExpr.parseShort(string);
		} else if (startsWithNullLiteral(string)) {
			return NullLiteral.parseShort(string);
		} else if (startsWithNumberLiteral(string)) {
			return NumberLiteral.parseShort(string);
		} else if (startsWithPostfixOperator(string)) {
			return PostfixOperator.parseShort(string);
		} else if (startsWithPrefixOperator(string)) {
			return PrefixOperator.parseShort(string);
		} else if (startsWithStringLiteral(string)){
			return StringLiteral.parseShort(string);
		} else { //then it is a regular string
			StringResult result = parseStringTillRPar(string);
			return new SingleResult(new StringExpr(result.getString()), result.getRest());
		}
	}

	public static boolean startsWithRPar(String string) {
		return string.startsWith(Names.RPar);
	}
}
