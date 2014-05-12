package literals;

import java.io.PrintStream;

import org.eclipse.jdt.core.dom.Assignment.Operator;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;

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

	public void addInfixExprLeft(InfixExpression.Operator operator, Expr leftExpr) {
		// TODO Auto-generated method stub
		
	}

	public void addInfixExprRight(InfixExpression.Operator operator, Expr rightExpr) {
		// TODO Auto-generated method stub
		
	}

	public void addPostfixExpr(PostfixExpression.Operator operator, Expr expr) {
		// TODO Auto-generated method stub
		
	}

	public void addPrefixExpr(PrefixExpression.Operator operator, Expr expr) {
		// TODO Auto-generated method stub
		
	}

}
