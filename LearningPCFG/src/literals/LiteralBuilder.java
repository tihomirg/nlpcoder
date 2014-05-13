package literals;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Assignment.Operator;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SuperFieldAccess;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import compositions.TypeBuilder;
import definitions.ArrayClassInfo;
import definitions.ClassInfo;
import definitions.Declaration;
import api.Imported;
import builders.SingleNodeVisitor;
import scopes.NameScopes;
import scopes.ScopesKeyValue;
import statistics.pretrees.Expr;
import statistics.pretrees.ExprFactory;
import statistics.pretrees.Variable;
import types.PrimitiveType;
import types.ReferenceType;
import types.StabileTypeFactory;
import types.Type;
import util.Pair;

public class LiteralBuilder extends SingleNodeVisitor {

	private Imported imported;
	private StabileTypeFactory typeFactory;

	private ExprFactory expFactory;
	private TypeBuilder typeBuilder;	

	private ScopesKeyValue<String, Pair<String, types.Type>> locals;
	private NameScopes params;

	private CompositionLiteralStatistics statistics;	
	
	private Expr expr;

	public LiteralBuilder(Imported imported, StabileTypeFactory typeFactory, TypeBuilder typeBuilder, NameScopes params, ScopesKeyValue<String, Pair<String, Type>> locals) {
		this.imported = imported;
		this.typeFactory = typeFactory;
		this.expFactory = new ExprFactory(typeFactory);
		this.typeBuilder = typeBuilder;
		this.params = params;
		this.locals = locals;
	}

	public void setExpr(Expr expr) {
		this.expr = expr;
	}

	public Expr getExpr(ASTNode exp){
		if (exp != null) exp.accept(this);
		else setExprToHole();
		return this.expr;
	}

	public boolean visit(ArrayAccess node) {
		Expr exp = getExpr(node.getArray());		
		Type type = exp.getType();

		if (type instanceof ReferenceType){
			ReferenceType refType = (ReferenceType) type;
			ClassInfo classInfo = refType.getClassInfo();
			if(classInfo != null){
				Declaration[] methods = classInfo.getMethods();
				Declaration[] compatible = getAllCompatible(methods, ArrayClassInfo.ACCESS);
				Expr index = getExpr(node.getIndex());
				Type[] argTypes = new Type[]{index.getType()};
				Declaration method = getFirstCompatible(compatible, argTypes, typeFactory);

				if (method != null){
					collect(method, new Expr[]{exp, index});
				}
			}
		} 
		setExprToHole();		

		return false;
	}

	public boolean visit(ArrayCreation node) {
		//Type type = typeBuilder.createType(node.getType());
		Expr[] args = getExprs(node.dimensions());
		Type[] argTypes = getTypes(args);

		if(compatibleWithIntType(argTypes)){
			ClassInfo aci = imported.getFirstType(ArrayClassInfo.SHORT_NAME);

			if(argTypes.length == 1){
				Declaration cons = aci.getConstructors()[0];				
				collect(cons, args);
			} else {
				Declaration cons = aci.getConstructors()[1];
				collect(cons, args);
			}
		}
		setExprToHole();

		return false;
	}

	public boolean visit(ArrayInitializer node) {
		setExprToHole();
		return false;
	}	

	public boolean visit(Assignment node) {
		Expr leftExp = getExpr(node.getLeftHandSide());
		Type leftType = leftExp.getType();
		Expr rightExp = getExpr(node.getRightHandSide());
		Type rightType = rightExp.getType();

		//System.out.println(leftType +"             "+rightType);

		if (leftType.isCompatible(rightType, typeFactory)){
			Operator operator = node.getOperator();
			
			collectAssignment(operator, leftExp.getType(), rightExp);
			
			if (operator.equals(Operator.ASSIGN)){
				if (leftExp instanceof Variable){
					Variable var = (Variable) leftExp;

					String name = var.getName();
					if (isLocalVariable(name)){
						Pair<String, Type> pair = locals.get(name);
						pair.setFirst(rightExp.shortRep());
						pair.setSecond(var.getType());
					}
					
//					if (isLocalVariable(name)){
//						Pair<String, Type> pair = locals.get(name);
//						pair.setFirst(var.shortRep());
//						pair.setSecond(var.getType());
//					}					
					
				}

			}
		} 
		setExprToHole();
		return false;
	}

	private void collectAssignment(Operator operator, Type type, Expr rightExp) {
		if (rightExp.isLiteral()){
			statistics.addAssignment(operator, type, rightExp);
		}
	}

	public boolean visit(BooleanLiteral node) {
		boolean val = node.booleanValue();
		setExpr(expFactory.createBooleanLiteral(val, typeFactory.createPrimitiveType("boolean")));
		return false;
	}

	public boolean visit(CastExpression node) {
		Expr exp = getExpr(node.getExpression());
		ReferenceType referenceType = this.typeBuilder.createReferenceType(node.getType());
		
		collectCastExpr(referenceType, exp);
		//setExpr(this.expFactory.createCastExpr(referenceType, exp));

		setExprToHole();
		
		return false;
	}

	private void collectCastExpr(ReferenceType referenceType, Expr exp) {
		if(exp.isLiteral()){
			statistics.addCastExpr(referenceType, exp);
		}
	}

	public boolean visit(CharacterLiteral node) {
		char val = node.charValue();
		setExpr(expFactory.createCharacterLiteral(val, typeFactory.createPrimitiveType("char")));
		return false;
	}

	public boolean visit(ClassInstanceCreation node){
		ReferenceType type = typeBuilder.createReferenceType(node.getType());
		ClassInfo classInfo = type.getClassInfo();

		if (classInfo != null){
			Declaration[] constructors = classInfo.getConstructors();
			Expr[] args = getExprs(node.arguments());

			Type[] argTypes = getTypes(args);

			Declaration cons = getFirstCompatible(constructors, argTypes, typeFactory);

			if (cons != null){
				collect(cons, args);
			}
		} 
		
		setExprToHole();

		return false;
	}

	private void collect(Declaration cons, Expr[] args) {
		for (int i = 0; i < args.length; i++) {
			Expr arg = args[i];
			if (arg.isLiteral()){
				statistics.addDeclInvocation(cons, i, arg);
			}
		}
	}	

	public boolean visit(ConditionalExpression node){

		Expr exp = getExpr(node.getExpression());
		Type type = exp.getType();

		if (compatibleWitBooleanType(type)){
			Expr elseExp = getExpr(node.getElseExpression());
			Expr thenExp = getExpr(node.getThenExpression());

			Type elseType = elseExp.getType();
			Type thenType = thenExp.getType();

			if (elseType.isCompatible(thenType, typeFactory) && thenType.isCompatible(elseType, typeFactory)){
				
				collectCondExpr(exp, thenExp, elseExp);
			} 
		}
		
		setExprToHole();

		return false;
	}

	private void collectCondExpr(Expr exp, Expr thenExp, Expr elseExp) {
		Expr[] exps = new Expr[]{exp, thenExp, elseExp};
		
		for (int i = 0; i < exps.length; i++) {
			Expr expr = exps[i];
			if (expr.isLiteral()){
				statistics.addCondExpr(i, expr);
			}
		}
	}

	public boolean visit(FieldAccess node) {
		String name = node.getName().getIdentifier();
		Expr exp = getExpr(node.getExpression());
		Type type = exp.getType();

		if (type instanceof ReferenceType){
			ReferenceType refType = (ReferenceType) type;
			ClassInfo classInfo = refType.getClassInfo();
			if(classInfo != null){
				Declaration[] fields = classInfo.getAllFields();

				Declaration field = getFirstCompatible(fields, name);

				if (field != null){
					collect(field, new Expr[]{exp});
				}

			}
		} 
		
		setExprToHole();

		return false;
	}

	public boolean visit(InfixExpression node){
		Expr leftExpr = getExpr(node.getLeftOperand());
		Expr rightExpr = getExpr(node.getRightOperand());

		Type leftType = leftExpr.getType();
		Type rightType = rightExpr.getType();

		if(leftType.isCompatible(rightType, typeFactory) && rightType.isCompatible(leftType, typeFactory)){
			InfixExpression.Operator operator = node.getOperator();

			//setExpr(this.expFactory.createInfixOperator(operator, leftExpr, rightExpr));
			
			collectInfixExpr(operator, leftExpr, rightExpr);
		} 
		
		setExprToHole();

		return false;
	}

	private void collectInfixExpr(org.eclipse.jdt.core.dom.InfixExpression.Operator operator, Expr leftExpr, Expr rightExpr) {
		if (leftExpr.isLiteral()){
			statistics.addInfixExprLeft(operator, leftExpr);
		}
		
		if (rightExpr.isLiteral()){
			statistics.addInfixExprRight(operator, rightExpr);			
		}
		
	}

	public boolean visit(InstanceofExpression node) {
		Expr exp = getExpr(node.getLeftOperand());
		ReferenceType referenceType = this.typeBuilder.createReferenceType(node.getRightOperand());
		//setExpr(this.expFactory.createInstOfExpr(exp, referenceType));

		collectInstOfExpr(exp, referenceType);
		
		setExprToHole();
		
		return false;
	}

	private void collectInstOfExpr(Expr exp, ReferenceType referenceType) {
		if(exp.isLiteral()){
			statistics.addInstOfExpr(exp, referenceType);
		}
	}

	public boolean visit(MethodInvocation node) {
		String name = node.getName().getIdentifier();
		Expr exp = getExpr(node.getExpression());

		Type type = exp.getType();

		if (type instanceof ReferenceType){
			ReferenceType refType = (ReferenceType) type;
			ClassInfo classInfo = refType.getClassInfo();
			if(classInfo != null){
				Declaration[] methods = classInfo.getAllMethods();
				Declaration[] compatible = getAllCompatible(methods, name);
				Expr[] args = getExprs(node.arguments());
				Type[] argTypes = getTypes(args);
				Declaration method = getFirstCompatible(compatible, argTypes, typeFactory);


				//System.out.println("Name: "+name);
				//System.out.println(Arrays.toString(compatible));

				//System.out.println(classInfo.getName() +"  "+Arrays.toString(argTypes));

				//TODO: Fix this such that method is never "null"
				if (method != null){
					//setExpr(expFactory.createMethodInvocation(method, exp, args));
					
					Expr[] exprs = new Expr[args.length+1];
					exprs[0] = exp;
					System.arraycopy(args, 0, exprs, 1, args.length);
					
					collect(method, exprs);
				}
			}
		}
		
		setExprToHole();

		return false;
	}

	public boolean visit(NullLiteral node){
		setExpr(expFactory.createNullLiteral(typeFactory.createNullType()));
		return false;
	}

	//Find exactly which number literal, i.e., its type.
	public boolean visit(NumberLiteral node){
		setExpr(expFactory.createNumberLiteral(node.getToken(), typeFactory.createPrimitiveType("int")));
		return false;
	}	

	public boolean visit(ParenthesizedExpression node) {
		setExpr(getExpr(node.getExpression()));
		return false;
	}

	public boolean visit(PostfixExpression node) {
		Expr expr = getExpr(node.getOperand());
		Type type = expr.getType();
		PostfixExpression.Operator operator = node.getOperator();
		//setExpr(this.expFactory.createPostfixOperator(operator, expr));		
		collectPostfixExpr(operator, expr);
		
		setExprToHole();
		return false;
	}

	private void collectPostfixExpr(org.eclipse.jdt.core.dom.PostfixExpression.Operator operator, Expr expr) {
		if (expr.isLiteral()){
			statistics.addPostfixExpr(operator, expr);
		}
	}

	public boolean visit(PrefixExpression node) {
		Expr expr = getExpr(node.getOperand());
		Type type = expr.getType();
		PrefixExpression.Operator operator = node.getOperator();
		//setExpr(this.expFactory.createPrefixOperator(operator, expr));
		collectPrefixExpr(operator, expr);
		
		setExprToHole();
		return false;
	}
	
	private void collectPrefixExpr(org.eclipse.jdt.core.dom.PrefixExpression.Operator operator, Expr expr) {
		if(expr.isLiteral()){
			statistics.addPrefixExpr(operator, expr);
		}
	}

	//Static methods, fields.
	public boolean visit(QualifiedName node) {
		setExprToHole();
		return false;
	}

	//TODO: Variables vs field, method, params,..
	public boolean visit(SimpleName node){
		String name = node.getIdentifier();
		if (!isParam(name)){
			if (isLocalVariable(name)){
				Pair<String, Type> value = locals.get(name);
				setExpr(expFactory.createVariable(name, value.getFirst(), value.getSecond()));
			} else if (isImportedType(name)){  //Should cover static fields/methods
				ClassInfo firstType = imported.getFirstType(name);
				setExpr(expFactory.createVariable(name, name, firstType.getType()));				
			} else setExprToHole();
		} else setExprToHole();
		return false;
	}

	private boolean isImportedType(String name) {
		return imported.isImportedType(name);
	}

	//TODO: Implement
	private boolean isLocalVariable(String name) {
		return locals.contains(name);
	}

	//TODO: Implement
	private boolean isParam(String name) {
		return params.contains(name);
	}

	public boolean visit(StringLiteral node) {
		setExpr(expFactory.createStringLiteral(node.getEscapedValue(), typeFactory.createConstType(java.lang.String.class.getName())));		
		return false;
	}

	public boolean visit(SuperFieldAccess node) {
		setExprToHole();
		return false;
	}	

	public boolean visit(SuperMethodInvocation node) {
		setExprToHole();
		return false;
	}

	public boolean visit(TypeLiteral node){
		setExprToHole();
		return false;
	}

	public boolean visit(VariableDeclarationExpression node){
		setExprToHole();
		return false;
	}

	// ---------------------------------------------------------------------- private methods ---------------------------------------------------------------	

	private boolean compatibleWitBooleanType(Type type) {
		return typeFactory.createPrimitiveType("boolean").isCompatible(type, typeFactory);
	}	
	private boolean compatibleWitBooleanType(Type[] argTypes) {
		PrimitiveType boolType = typeFactory.createPrimitiveType("boolean");

		for (Type type : argTypes) {
			if(!boolType.isCompatible(type, typeFactory)) return false;
		}

		return true;
	}

	private boolean compatibleWithIntType(Type[] argTypes) {
		PrimitiveType intType = typeFactory.createPrimitiveType("int");

		for (Type type : argTypes) {
			if(!intType.isCompatible(intType, typeFactory)) return false;
		}

		return true;
	}

	private Declaration[] getAllCompatible(Declaration[] decls, String name) {
		List<Declaration> list = new LinkedList<Declaration>();

		for (Declaration decl : decls) {
			if (decl.getSimpleName().equals(name)){
				list.add(decl);
			}
		}

		return list.toArray(new Declaration[list.size()]);
	}	

	private Expr[] getExprs(List<ASTNode> arguments) {
		int size = arguments.size();
		Expr[] args = new Expr[size];
		for (int i = 0; i < size; i++) {
			args[i] = getExpr(arguments.get(i));
		}
		return args;
	}

	private Declaration getFirstCompatible(Declaration[] decls, String name) {

		for (Declaration decl : decls) {
			if (decl.getSimpleName().equals(name)) return decl;
		}

		return null;
	}

	private Declaration getFirstCompatible(Declaration[] decls, Type[] argTypes, StabileTypeFactory factory) {
		for (Declaration decl: decls) {
			if (decl.isCompatible(argTypes, factory)){
				return decl;
			}
		}
		return null;
	}

	private Type[] getTypes(Expr[] exprs) {
		Type[] types = new Type[exprs.length];

		for (int i=0; i < types.length; i++) {
			types[i] = exprs[i].getType();
		}

		return types;
	}	

	private void setExprToHole() {
		setExpr(expFactory.createHole());
	}

	public void print(PrintStream out) {
		statistics.print(out);
	}
}
