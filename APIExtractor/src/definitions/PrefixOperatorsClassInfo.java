package definitions;

import org.eclipse.jdt.core.dom.PostfixExpression.Operator;

import types.InitialTypeFactory;
import types.ReferenceType;
import types.Type;
import types.Variable;

public class PrefixOperatorsClassInfo extends ClassInfo {
	
	private static final String SHORT_NAME = "Prefix";
	private static final String PKG_NAME = "";
	private static final String LONG_NAME = PKG_NAME+SHORT_NAME;
	
	private static final Operator[] ops = new Operator[]{
			Operator.DECREMENT,
			Operator.INCREMENT
	};
	
	public PrefixOperatorsClassInfo(){}
	
	public PrefixOperatorsClassInfo(InitialTypeFactory tf) {
		String name = LONG_NAME;
		this.setName(name);
		this.setClass(true);
		this.setPublic(true);
		this.setSimpleName(SHORT_NAME);
		this.setPackageName(PKG_NAME);

		this.setInheritedTypes(new ReferenceType[0]);

		this.setType(tf.createNoVariableType());

		this.setConstructors(new Declaration[0]);
		this.setFields(new Declaration[0]);
		this.setMethods(createMethods(tf));
	}

	private Declaration[] createMethods(InitialTypeFactory tf) {
		Declaration[] decls = new Declaration[ops.length];
		for (int i = 0; i < decls.length; i++) {
			decls[i] = createMethod(ops[i], tf);
		}
		return decls;
	}

	private Declaration createMethod(Operator op, InitialTypeFactory tf) {
		Declaration decl = new Declaration();

		String clazzName = this.getName();
		decl.setClazz(clazzName);
		decl.setPackageName(this.getPackageName());
		decl.setName(op.toString());
		decl.setMethod(true);
		decl.setStatic(true);
		decl.setPublic(true);		
		
		Variable variable = tf.createNewVariable();
		
		decl.setRetType(variable);
		Type[] args = new Type[]{variable, variable};
		decl.setArgTypes(args);
		decl.setArgNum(args.length);

		return decl;
	}
}