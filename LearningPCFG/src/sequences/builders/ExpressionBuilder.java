package sequences.builders;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.EmptyStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;

import declarations.Imported;
import definitions.ArrayClassInfo;
import definitions.ClassInfo;
import definitions.Declaration;

import builders.FalseBuilder;

import selection.types.PrimitiveType;
import selection.types.ReferenceType;
import selection.types.StabileTypeFactory;
import selection.types.Type;
import selection.types.TypeFactory;
import sequences.trees.Expr;
import sequences.trees.ExprFactory;

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

	private Expr expr;
	private ExprFactory expFactory;
	private StabileTypeFactory typeFactory;
	private TypeBuilder typeBuilder;
	private Imported imported;

	private Expr getExpr(ASTNode exp){
		if (expr != null)
			exp.accept(this);
		else 
			this.expr = expFactory.createHole();
		return this.expr;
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
			this.expr = expFactory.createConstructorInvocation(cons, args);
		} else this.expr = expFactory.createHole();

		return false;
	}

	private Type[] getTypes(Expr[] exprs) {
		Type[] types = new Type[exprs.length];

		for (int i=0; i < types.length; i++) {
			types[i] = exprs[i].getType();
		}

		return types;
	}

	private Declaration getFirstCompatible(Declaration[] decls, Type[] argTypes, StabileTypeFactory factory) {
		for (Declaration decl: decls) {
			if (decl.isCompatible(argTypes, factory)){
				return decl;
			}
		}
		return null;
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
				this.expr = expFactory.createFieldAccess(field, exp, typeFactory.createNoType());

			} else this.expr = expFactory.createHole();

		} else this.expr = expFactory.createHole();

		return false;
	}

	private Declaration getFirstCompatible(Declaration[] decls, String name) {
		
		for (Declaration decl : decls) {
			if (decl.getSimpleName().equals(name)) return decl;
		}
		
		return null;
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
				
				this.expr = expFactory.createMethodInvocation(method, exp, args);

			} else this.expr = expFactory.createHole();

		} else this.expr = expFactory.createHole();		
		

		return false;
	}

	private Expr[] getExprs(List<ASTNode> arguments) {
		int size = arguments.size();
		Expr[] args = new Expr[size];
		for (int i = 0; i < size; i++) {
			args[i] = getExpr(arguments.get(i));
		}
		return args;
	}

	public boolean visit(BooleanLiteral node) {
		boolean val = node.booleanValue();
		this.expr = expFactory.createLiteral(Boolean.toString(val), typeFactory.createPrimitiveType("boolean"));
		return false;
	}

	public boolean visit(CharacterLiteral node) {
		char val = node.charValue();
		this.expr = expFactory.createLiteral(Character.toString(val), typeFactory.createPrimitiveType("char"));
		return false;
	}

	public boolean visit(NullLiteral node){
		this.expr = expFactory.createLiteral("null", typeFactory.createNullType());
		return false;
	}

	//Find exactly which number literal, i.e., its type.
	public boolean visit(NumberLiteral node){
		this.expr = expFactory.createLiteral(node.getToken(), typeFactory.createConstType("java.lang.Object"));
		return false;
	}

	public boolean visit(StringLiteral node) {
		this.expr = expFactory.createLiteral(node.getEscapedValue(), typeFactory.createConstType("java.lang.String"));		
		return false;
	}	

    //TODO: Variables vs field, method, params,..
	public boolean visit(SimpleName node){
		String name = node.getIdentifier();
		this.expr = expFactory.createVariable(name, typeFactory.createNoType());
		return false;
	}

	//Static methods, fields.
	public boolean visit(QualifiedName node) {
		this.expr = expFactory.createHole();
		return false;
	}

	public boolean visit(ConstructorInvocation node) {
		this.expr = expFactory.createHole();
		return false;
	}

	public boolean visit(EmptyStatement node) {
		this.expr = expFactory.createHole();
		return false;
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
				
				this.expr = expFactory.createMethodInvocation(method, exp, new Expr[]{index});
				
			} else this.expr = expFactory.createHole();

		} else this.expr = expFactory.createHole();		
		

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
				
				this.expr = expFactory.createConstructorInvocation(cons, args);
				
			} else {
				Declaration cons = aci.getConstructors()[1];
				
				this.expr = expFactory.createConstructorInvocation(cons, args);				
			}
		} else {
			
		}
		
		return false;
	}

	private boolean compatibleWithIntType(Type[] argTypes) {
		PrimitiveType intType = typeFactory.createPrimitiveType("int");
		
		for (Type type : argTypes) {
			if(!intType.isCompatible(intType, typeFactory)) return false;
		}
		
		return true;
	}

	public boolean visit(ArrayInitializer node) {
		this.expr = expFactory.createHole();
		return false;
	}
}
