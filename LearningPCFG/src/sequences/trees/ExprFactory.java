package sequences.trees;

import definitions.Declaration;
import selection.types.Type;
import selection.types.TypeFactory;

public class ExprFactory {
	
	private TypeFactory typeFactory;
	
	private final Expr hole;

	public ExprFactory(TypeFactory typeFactory) {
		this.typeFactory = typeFactory;
		this.hole = new Hole(typeFactory.createNoType());
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
}
