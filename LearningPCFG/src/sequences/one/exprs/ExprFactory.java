package sequences.one.exprs;

import org.eclipse.jdt.core.dom.Assignment.Operator;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import definitions.Declaration;
import selection.types.PrimitiveType;
import selection.types.ReferenceType;
import selection.types.Type;
import selection.types.StabileTypeFactory;

public class ExprFactory {
	private final Expr hole;
	private final Type boolType;
	
	public ExprFactory(StabileTypeFactory typeFactory) {
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

	public Expr createVariable(String name, String value, Type type) {
		return new Variable(name, value, type);
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

	public Expr createInfixOperator(InfixExpression.Operator operator, Expr leftExpr, Expr rightExpr) {
		return new InfixOperator(operator, leftExpr, rightExpr);
	}

	public Expr createPostfixOperator(PostfixExpression.Operator operator, Expr expr) {
		return new PostfixOperator(operator, expr);
	}

	public Expr createPrefixOperator(PrefixExpression.Operator operator, Expr expr) {
		return new PrefixOperator(operator, expr);
	}

	public Expr createBooleanLiteral(boolean val, PrimitiveType type) {
		return new BooleanLiteral(val, type);
	}

	public Expr createCharacterLiteral(char value, PrimitiveType type) {
		return new CharacterLiteral(value, type);
	}

	public Expr createNullLiteral(Type type) {
		return new NullLiteral(type);
	}

	public Expr createNumberLiteral(String number, Type type) {
		return new NumberLiteral(number, type);
	}

	public Expr createStringLiteral(String value, Type type) {
		return new StringLiteral(value, type);
	}
}
