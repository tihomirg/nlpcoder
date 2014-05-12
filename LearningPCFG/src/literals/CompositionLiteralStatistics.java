package literals;

import java.io.PrintStream;

import org.eclipse.jdt.core.dom.Assignment.Operator;

import statistics.pretrees.Expr;
import types.ReferenceType;
import types.Type;
import definitions.Declaration;

public class CompositionLiteralStatistics {

	public void addDeclInvocation(Declaration cons, int i, Expr arg) {
		// TODO Auto-generated method stub
		
	}

	public void addAssignment(Operator operator, Type type, Expr rightExp) {
		// TODO Auto-generated method stub
		
	}

	public void print(PrintStream out) {
		// TODO Auto-generated method stub
		
	}

	public void addCondExpr(int i, Expr expr) {
		// TODO Auto-generated method stub
	}

	public void addInstOfExpr(Expr exp, ReferenceType referenceType) {
		// TODO Auto-generated method stub
		
	}

	public void addCastExpr(ReferenceType referenceType, Expr exp) {
		// TODO Auto-generated method stub
		
	}

	public void addInfixExprLeft(
			org.eclipse.jdt.core.dom.InfixExpression.Operator operator,
			Expr leftExpr) {
		// TODO Auto-generated method stub
		
	}

	public void addInfixExprRight(
			org.eclipse.jdt.core.dom.InfixExpression.Operator operator,
			Expr rightExpr) {
		// TODO Auto-generated method stub
		
	}

	public void addPostfixExpr(
			org.eclipse.jdt.core.dom.PostfixExpression.Operator operator,
			Expr expr) {
		// TODO Auto-generated method stub
		
	}

	public void addPrefixExpr(
			org.eclipse.jdt.core.dom.PrefixExpression.Operator operator,
			Expr expr) {
		// TODO Auto-generated method stub
		
	}

}
