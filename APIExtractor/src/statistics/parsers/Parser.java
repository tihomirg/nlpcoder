package statistics.parsers;

import java.util.Map;

import definitions.Declaration;
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
import types.StabileTypeFactory;
import types.Type;

public class Parser {

	private static final String BOOLEAN = "boolean";
	private static final String CHAR = "char";
	private static Map<Integer, Declaration> decls;
	private static StabileTypeFactory tf;
	
	private static Expr HOLE;
	private static Expr NULL;

	public static void init(){
		HOLE = new statistics.posttrees.Hole(tf.createNoType());
		NULL = new statistics.posttrees.NullLiteral(tf.createNullType());		
	}

	public static Expr parse(String string){
		if (startsWithAssignment(string)){
			return Assignment.parse(string, tf);
		} else if (startsWithBooleanLiteral(string)) {
			return BooleanLiteral.parse(string, tf);
		} else if (startsWithCastExpr(string)) {
			return CastExpr.parse(string, tf);
		} else if (startsWithCharacterLiteral(string)) {
			return CharacterLiteral.parse(string, tf);
		} else if (startsWithCondExpr(string)) {
			return CondExpr.parse(string, tf);
		} else if (startsWithConstructorInvocation(string)) {
			return ConstructorInvocation.parse(string, tf);
		} else if (startsWithHole(string)) {
			return Hole.parse(string, tf);
		} else if (startsWithInfixOperator(string)) {
			return InfixOperator.parse(string, tf);
		} else if (startsWithInstanceFieldAccess(string)) {
			return InstanceFieldAccess.parse(string, tf);
		} else if (startsWithInstanceMethodInvocation(string)) {
			return InstanceMethodInvocation.parse(string, tf);
		} else if (startsWithInstOfExpr(string)) {
			return InstOfExpr.parse(string, tf);
		} else if (startsWithNullLiteral(string)) {
			return NullLiteral.parse(string, tf);
		} else if (startsWithNumberLiteral(string)) {
			return NumberLiteral.parse(string, tf);
		} else if (startsWithPostfixOperator(string)) {
			return PostfixOperator.parse(string, tf);
		} else if (startsWithPrefixOperator(string)) {
			return PrefixOperator.parse(string, tf);
		} else {//it is startsWithStringLiteral(string)
			return StringLiteral.parse(string, tf);
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
	
	public static IntResult parseIntTillRPar(String string){
		String op = string.substring(0, string.indexOf(Names.RPar));
		int val = Integer.parseInt(op);
		return new IntResult(val, string.substring(op.length()));
	}	

	public static SingleResult parseShort(String string, StabileTypeFactory tf) {
		if (startsWithAssignment(string)){
			return Assignment.parseShort(string, tf);
		} else if (startsWithBooleanLiteral(string)) {
			return BooleanLiteral.parseShort(string, tf);
		} else if (startsWithCastExpr(string)) {
			return CastExpr.parseShort(string, tf);
		} else if (startsWithCharacterLiteral(string)) {
			return CharacterLiteral.parseShort(string, tf);
		} else if (startsWithCondExpr(string)) {
			return CondExpr.parseShort(string, tf);
		} else if (startsWithConstructorInvocation(string)) {
			return ConstructorInvocation.parseShort(string, tf);
		} else if (startsWithHole(string)) {
			return Hole.parseShort(string, tf);
		} else if (startsWithInfixOperator(string)) {
			return InfixOperator.parseShort(string, tf);
		} else if (startsWithInstanceFieldAccess(string)) {
			return InstanceFieldAccess.parseShort(string, tf);
		} else if (startsWithInstanceMethodInvocation(string)) {
			return InstanceMethodInvocation.parseShort(string, tf);
		} else if (startsWithInstOfExpr(string)) {
			return InstOfExpr.parseShort(string, tf);
		} else if (startsWithNullLiteral(string)) {
			return NullLiteral.parseShort(string, tf);
		} else if (startsWithNumberLiteral(string)) {
			return NumberLiteral.parseShort(string, tf);
		} else if (startsWithPostfixOperator(string)) {
			return PostfixOperator.parseShort(string, tf);
		} else if (startsWithPrefixOperator(string)) {
			return PrefixOperator.parseShort(string, tf);
		} else if (startsWithStringLiteral(string)){
			return StringLiteral.parseShort(string, tf);
		} else { //then it is a regular string
			StringResult result = parseStringTillRPar(string);
			return new SingleResult(new StringExpr(result.getString()), result.getRest());
		}
	}

	public static boolean startsWithRPar(String string) {
		return string.startsWith(Names.RPar);
	}
	
	public static statistics.posttrees.Expr createAssignment(String op, Type type) {
		return new statistics.posttrees.Assignment(op, type);
	}

	public static statistics.posttrees.Expr createBooleanLiteral() {
		return new statistics.posttrees.BooleanLiteral(tf.createPrimitiveType(BOOLEAN));
	}

	public static statistics.posttrees.Expr createCastExpr(Type type, Type argType) {
		return new statistics.posttrees.CastExpr(type, argType);
	}

	public static statistics.posttrees.Expr createCharacterLiteral() {
		return new statistics.posttrees.CharacterLiteral(tf.createPrimitiveType(CHAR));
	}	
	
	public static statistics.posttrees.Expr createCondExpr(Type retType) {
		return new statistics.posttrees.CondExpr(tf.createPrimitiveType(BOOLEAN), retType);
	}

	public static statistics.posttrees.Expr createConstructorInvocation(int id) {
		return new statistics.posttrees.ConstructorInvocation(decls.get(id));
	}

	public static statistics.posttrees.Expr createHole() {
		return HOLE;
	}

	public static statistics.posttrees.Expr createInfixOperator(String op, Type type) {
		return new statistics.posttrees.InfixOperator(op, type);
	}

	public static statistics.posttrees.Expr createInstanceFieldAccess(int id) {
		return new statistics.posttrees.InstanceFieldAccess(decls.get(id));
	}

	public static statistics.posttrees.Expr createInstanceMethodInvocation(int id) {
		return new statistics.posttrees.InstanceMethodInvocation(decls.get(id));
	}

	public static statistics.posttrees.Expr createInstOfExpr(Type type) {
		return new statistics.posttrees.InstOfExpr(type, tf.createNewVariable());
	}

	public static statistics.posttrees.Expr createNullLiteral() {
		return NULL;
	}

	public static statistics.posttrees.Expr createNumberLiteral() {
		return new statistics.posttrees.NumberLiteral(tf.createPrimitiveType("int"));
	}

	public static statistics.posttrees.Expr createPostfixOperator(String op, Type type) {
		return new statistics.posttrees.PostfixOperator(op, type);
	}

	public static statistics.posttrees.Expr createPrefixOperator(String op, Type type) {
		return new statistics.posttrees.PrefixOperator(op, type);
	}

	public static statistics.posttrees.Expr createStringLiteral() {
		return new statistics.posttrees.StringLiteral(tf.createConstType(java.lang.String.class));
	}
	
	public static IntResult getStatistics(String line) {
		String number = line.substring(0, line.indexOf(Names.Colon));
		return new IntResult(Integer.parseInt(number), line.substring(line.indexOf(Names.Colon)+Names.Colon.length()));
	}

	public static void setDecls(Map<Integer, Declaration> decls) {
		Parser.decls = decls;
	}
	
	public static void setTf(StabileTypeFactory tf) {
		Parser.tf = tf;
	}
}
