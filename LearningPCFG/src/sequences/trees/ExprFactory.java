package sequences.trees;

import selection.types.Const;
import selection.types.Type;
import selection.types.TypeFactory;

public class ExprFactory {
	
	private TypeFactory typeFactory;
	
	private final Expr hole;

	public ExprFactory(TypeFactory typeFactory) {
		this.typeFactory = typeFactory;
		this.hole = new Hole(typeFactory.getNoType());
	}
	
	public Expr getHole() {
		return hole;
	}

	public Expr createFieldAccess(String name, Expr exp, Type type) {
		return new InstanceFieldAccess(name, exp, type);
		
	}

	public Expr createMethodInvocation(String name, Expr exp, Expr[] args, Type type) {
		return new InstanceMethodInvocation(name, exp, args, type);
	}

	public Expr createConstructorInvocation(Type type, Expr[] args) {
		return new ConstructorInvocation(type, args);
	}

	public Expr createLiteral(String literal, Type type) {
		return new Literal(literal, type);
	}

	public Expr createVariable(String name, Type type) {
		return new Variable(name, type);
	}
}
