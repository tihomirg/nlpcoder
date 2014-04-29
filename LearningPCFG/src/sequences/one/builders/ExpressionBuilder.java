package sequences.one.builders;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.EmptyStatement;
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

import declarations.Imported;
import definitions.ArrayClassInfo;
import definitions.ClassInfo;
import definitions.Declaration;
import builders.FalseBuilder;
import selection.types.PrimitiveType;
import selection.types.ReferenceType;
import selection.types.StabileTypeFactory;
import selection.types.Type;
import sequences.one.exprs.Expr;
import sequences.one.exprs.ExprFactory;

//Expression:	
//Annotation,
//ArrayAccess,
//ArrayCreation,
//ArrayInitializer,
//Assignment,
//BooleanLiteral,
//CastExpression,
//CharacterLiteral,
//ClassInstanceCreation,
//ConditionalExpression,
//FieldAccess,
//InfixExpression,
//InstanceofExpression,
//MethodInvocation,
//Name,
//NullLiteral,
//NumberLiteral,
//ParenthesizedExpression,
//PostfixExpression,
//PrefixExpression,
//StringLiteral,
//SuperFieldAccess,
//SuperMethodInvocation,
//ThisExpression,
//TypeLiteral,
//VariableDeclarationExpression

public class ExpressionBuilder extends FalseBuilder {

	private ExprFactory expFactory;
	private Expr expr;
	private Imported imported;
	private TypeBuilder typeBuilder;
	private StabileTypeFactory typeFactory;

	public void setExpr(Expr expr) {
		this.expr = expr;
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
				
				setExpr(expFactory.createMethodInvocation(method, exp, new Expr[]{index}));
				
			} else setExprToHole();

		} else setExprToHole();		

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
				setExpr(expFactory.createConstructorInvocation(cons, args));
			} else {
				Declaration cons = aci.getConstructors()[1];
				
				//TODO: Create another array, or come up with the alternative solution
				//Go up to n-dimensional array.
				setExpr(expFactory.createConstructorInvocation(cons, args));				
			}
		} else {
			setExprToHole();
		}
		
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
		
		if (leftType.isCompatible(rightType, typeFactory)){
			setExpr(this.expFactory.createAssignment(node.getOperator(), leftExp, rightExp));
		} else setExprToHole();
		
		return false;
	}	
	
	public boolean visit(BooleanLiteral node) {
		boolean val = node.booleanValue();
		setExpr(expFactory.createBooleanLiteral(val, typeFactory.createPrimitiveType("boolean")));
		return false;
	}
	
	public boolean visit(CastExpression node) {
		Expr exp = getExpr(node.getExpression());
		ReferenceType referenceType = this.typeBuilder.createReferenceType(node.getType());
		setExpr(this.expFactory.createCastExpr(referenceType, exp));
		
		return false;
	}
	
	public boolean visit(CharacterLiteral node) {
		char val = node.charValue();
		setExpr(expFactory.createCharacterLiteral(val, typeFactory.createPrimitiveType("char")));
		return false;
	}

	public boolean visit(ClassInstanceCreation node){

		//We need to cover a case when constructor of a user class is invoked
		//i.e. classInfo will be null in this case.

		ReferenceType type = typeBuilder.createReferenceType(node.getType());
		ClassInfo classInfo = type.getClassInfo();

		if (classInfo != null){
			Declaration[] constructors = classInfo.getConstructors();
			Expr[] args = getExprs(node.arguments());
			Type[] argTypes = getTypes(args);

			Declaration cons = getFirstCompatible(constructors, argTypes, typeFactory);
			setExpr(expFactory.createConstructorInvocation(cons, args));
		} else setExprToHole();

		return false;
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
				setExpr(this.expFactory.createCondExpr(exp, thenExp, elseExp));
			} else setExprToHole();
			
		} else setExprToHole();
		
		return false;
	}	
	
	public boolean visit(ConstructorInvocation node) {
		setExprToHole();
		return false;
	}

	public boolean visit(EmptyStatement node) {
		setExprToHole();
		return false;
	}	
	
	public boolean visit(FieldAccess node) {
		String name = node.getName().getIdentifier();
		Expr exp = getExpr(node.getExpression());
		Type type = exp.getType();

		if (type instanceof ReferenceType){
			ReferenceType refType = (ReferenceType) type;
			ClassInfo classInfo = refType.getClassInfo();
			if(classInfo != null){
				Declaration[] fields = classInfo.getFields();
				
				Declaration field = getFirstCompatible(fields, name);
				setExpr(expFactory.createFieldAccess(field, exp, typeFactory.createNoType()));

			} else setExprToHole();

		} else setExprToHole();

		return false;
	}

	public boolean visit(InfixExpression node){
		Expr leftExpr = getExpr(node.getLeftOperand());
		Expr rightExpr = getExpr(node.getRightOperand());
		
		Type leftType = leftExpr.getType();
		Type rightType = rightExpr.getType();
		
		if(leftType.isCompatible(rightType, typeFactory) && rightType.isCompatible(leftType, typeFactory)){
			InfixExpression.Operator operator = node.getOperator();

			setExpr(this.expFactory.createInfixOperator(operator, leftExpr, rightExpr));
		} else setExprToHole();
		
		return false;
	}

	public boolean visit(InstanceofExpression node) {
		Expr exp = getExpr(node.getLeftOperand());
		ReferenceType referenceType = this.typeBuilder.createReferenceType(node.getRightOperand());
		setExpr(this.expFactory.createInstOfExpr(exp, referenceType));
		
		return false;
	}

	public boolean visit(MethodInvocation node) {
		String name = node.getName().getIdentifier();
		Expr exp = getExpr(node.getExpression());
		
		Type type = exp.getType();
		
		if (type instanceof ReferenceType){
			ReferenceType refType = (ReferenceType) type;
			ClassInfo classInfo = refType.getClassInfo();
			if(classInfo != null){
				Declaration[] methods = classInfo.getMethods();
				Declaration[] compatible = getAllCompatible(methods, name);
				Expr[] args = getExprs(node.arguments());
				Type[] argTypes = getTypes(args);
				Declaration method = getFirstCompatible(compatible, argTypes, typeFactory);
				
				setExpr(expFactory.createMethodInvocation(method, exp, args));

			} else setExprToHole();

		} else setExprToHole();
		

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
		setExpr(this.expFactory.createPostfixOperator(operator, expr));
		return false;
	}

	public boolean visit(PrefixExpression node) {
		Expr expr = getExpr(node.getOperand());
		Type type = expr.getType();
		PrefixExpression.Operator operator = node.getOperator();
		setExpr(this.expFactory.createPrefixOperator(operator, expr));		
		return false;
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
				setExpr(expFactory.createVariable(name, typeFactory.createNoType()));				
			}
		}
		return false;
	}

	//TODO: Implement
	private boolean isLocalVariable(String name) {
		return true;
	}

	//TODO: Implement
	private boolean isParam(String name) {
		return false;
	}

	public boolean visit(StringLiteral node) {
		setExpr(expFactory.createStringLiteral(node.getEscapedValue(), typeFactory.createConstType("java.lang.String")));		
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

	//Initializer of the for statement, basically the outer expression.
	//Will come back to it when loops come into play.
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

	private Expr getExpr(ASTNode exp){
		if (expr != null) exp.accept(this);
		else setExprToHole();
		return this.expr;
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
}
