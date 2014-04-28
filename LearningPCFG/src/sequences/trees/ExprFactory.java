package sequences.trees;

import org.eclipse.jdt.core.dom.Assignment.Operator;

import definitions.Declaration;
import selection.types.ReferenceType;
import selection.types.Type;
import selection.types.TypeFactory;

public class ExprFactory {
	
	//private TypeFactory typeFactory;
	
	private final Expr hole;
	private final Type boolType;
	
	public ExprFactory(TypeFactory typeFactory) {
//		this.typeFactory = typeFactory;
		this.hole = new Hole(typeFactory.createNoType());
		this.boolType = typeFactory.createPrimitiveType("boolean");
	}
	
	public Expr createHole() {
		return hole;
	}

	public Expr createFieldAccess(Declaration field, Expr exp, Type type) {
		return new InstanceFieldAccess(field, exp, type);
	}

	public Expr createMethodInvocation(Declaration method, Expr exp, Expr[] args) {
		return new InstanceMethodInvocation(method, exp, args);
	}

	public Expr createConstructorInvocation(Declaration cons, Expr[] args) {
		return new ConstructorInvocation(cons, args);
	}

	public Expr createLiteral(String literal, Type type) {
		return new Literal(literal, type);
	}

	public Expr createVariable(String name, Type type) {
		return new Variable(name, type);
	}

	public Expr createCondExpr(Expr exp, Expr thenExp, Expr elseExp) {
		return new CondExpr(exp, thenExp, elseExp);
	}

	public Expr createAssignment(Operator operator, Expr leftExp, Expr rightExp) {
		return new Assignment(operator, leftExp, rightExp);
	}

	public Expr createInstOfExpr(Expr exp, Type type) {
		return new InstOfExpr(exp, type, boolType);
	}

	public Expr createCastExpr(ReferenceType referenceType, Expr exp) {
		return new CastExpr(referenceType, exp);
	}
}
