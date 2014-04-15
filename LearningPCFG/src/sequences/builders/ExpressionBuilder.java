package sequences.builders;

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

import builders.FalseBuilder;

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
	private TypeFactory typeFactory;
	
	private TypeBuilder typeBuilder;

	private Expr getExpr(ASTNode exp){
		if (expr != null)
			exp.accept(this);
		else 
			this.expr = expFactory.getHole(); 
		return this.expr;
	}
	
	public boolean visit(ClassInstanceCreation node){
		Type type = typeBuilder.createType(node.getType());
		Expr[] args = getExprs(node.arguments());
		this.expr = expFactory.createConstructorInvocation(type, args);
		return false; 
	}

	public boolean visit(FieldAccess node) {
		String name = node.getName().getIdentifier();
		Expr exp = getExpr(node.getExpression());
		this.expr = expFactory.createFieldAccess(name, exp, typeFactory.getNoType());
		return false;
	}

	public boolean visit(MethodInvocation node) {
		String name = node.getName().getIdentifier();
		Expr exp = getExpr(node.getExpression());
		Expr[] args = getExprs(node.arguments());
		this.expr = expFactory.createMethodInvocation(name, exp, args, typeFactory.getNoType());
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

	public boolean visit(NumberLiteral node){
		this.expr = expFactory.createLiteral(node.getToken(), typeFactory.createConstType("java.lang.Object"));
		return false;
	}

	public boolean visit(StringLiteral node) {
		this.expr = expFactory.createLiteral(node.getEscapedValue(), typeFactory.createConstType("java.lang.String"));		
		return false;
	}	

	public boolean visit(SimpleName node){
		String name = node.getIdentifier();
		this.expr = expFactory.createVariable(name, typeFactory.getNoType());
		return false;
	}
	
	public boolean visit(QualifiedName node) {
		this.expr = expFactory.getHole();
		return false;
	}

	public boolean visit(ConstructorInvocation node) {
		this.expr = expFactory.getHole();
		return false;
	}

	public boolean visit(EmptyStatement node) {
		this.expr = expFactory.getHole();
		return false;
	}	
	public boolean visit(ArrayAccess node) {
		Expr expr = getExpr(node.getArray());		
		Expr index = getExpr(node.getIndex());
		this.expr = expFactory.createMethodInvocation("[]", expr, new Expr[]{index}, typeFactory.getNoType());
		return false;
	}

	public boolean visit(ArrayCreation node) {
		Type type = typeBuilder.createType(node.getType());
		Expr[] args = getExprs(node.dimensions());
		this.expr = expFactory.createConstructorInvocation(type, args);
		return false;
	}

	public boolean visit(ArrayInitializer node) {
		this.expr = expFactory.getHole();
		return false;
	}
}
