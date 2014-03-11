package interpreter;

import lexicalized.info.ArrayCreationInfo;
import lexicalized.info.ClassInstanceCreationInfo;
import lexicalized.info.FieldInfo;
import lexicalized.info.InfixExpressionInfo;
import lexicalized.info.LexicalizedInfo;
import lexicalized.info.MethodInfo;
import lexicalized.info.PostfixExpressionInfo;
import lexicalized.info.PrefixExpressionInfo;
import lexicalized.info.SimpleNameInfo;
import lexicalized.symbol.LexicalizedNonTerminal;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SuperFieldAccess;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.ThisExpression;

import builders.FalseBuilder;

import scopes.SimpleScopes;
import symbol.Symbol;
import util.List;
import config.Config;

public class ENonTerminalFactory {
	
	private LNonTerminalEvaluator evaluator;
	
	public ENonTerminalFactory(SimpleScopes scopes) {
		this.evaluator = new LNonTerminalEvaluator(scopes);
	}
	
    public Symbol lNonTerminal(ASTNode node){
		node.accept(this.evaluator);
		LexicalizedNonTerminal result = this.evaluator.getResult();
		
    	return result != null ? result: Config.getFactory().getNonTerminal(node);
    }
    
    public List<Symbol> makeLNonTerminalList(java.util.List<ASTNode> nodes) {
		return makeLNonTerminalList(nodes.toArray(new ASTNode[0]));
	}
	
    public List<Symbol> makeLNonTerminalList(ASTNode[] nodes) {
		List<Symbol> list = new List<Symbol>();
		for(ASTNode node: nodes){
			list.add(lNonTerminal(node));
		}
		return list;
	}
}

class LNonTerminalEvaluator extends LNonTerminalBuilder {

	private Evaluator evaluator;
	
	public LNonTerminalEvaluator(SimpleScopes scopes) {
		super(scopes);
		this.evaluator = new Evaluator(scopes);
	}

	protected LexicalizedNonTerminal lSimpleNameNonTerminal(SimpleName name) {
		LexicalizedNonTerminal nt = lNonTerminal0(name, new SimpleNameInfo(name.getIdentifier()));
		
		LexicalizedNonTerminal result = null;
		if (nt.isUserDef()){
			String name1 = nt.getInfo().getName();
			ASTNode node = scopes.getValue(name1);
		
		    name.accept(evaluator);
		    
		    result = evaluator.getResult();
		    
		    
		}
		
		return result != null? result : nt;
	}

}

class Evaluator extends FalseBuilder {

	private SimpleScopes scopes;		
	
	private LexicalizedNonTerminal result;
	
	public Evaluator(SimpleScopes scopes) {
		this.scopes = scopes;
	}

	public boolean visit(MethodInvocation node){
		setResult(lMethodNonTerminal(node));
		return false;
	}

	public boolean visit(ArrayCreation node){
		setResult(lArrayCreationNonTerminal(node));
		return false;
	}
	
	public boolean visit(ClassInstanceCreation node){
		setResult(lClassInstanceCreationNonTerminal(node));
		return false;
	}
	
	public boolean visit(InfixExpression node){
		setResult(lInfixExpressionNonTerminal(node));
		return false;
	}
	
	public boolean visit(PostfixExpression node){
		setResult(lPostfixExpressionNonTerminal(node));
		return false;
	}
	
	public boolean visit(PrefixExpression node){
		setResult(lPrefixExpressionNonTerminal(node));
		return false;
	}
	
	
	public void endVisit(Assignment node) {
		if(node.getOperator().equals(Assignment.Operator.ASSIGN)){
		  Expression lhs = node.getLeftHandSide();
		
		  if (lhs instanceof SimpleName){
			SimpleName variable = (SimpleName) lhs;
			scopes.changeValue(variable.getIdentifier(), tunnel(node.getRightHandSide()));
		  } else 
		  if(lhs instanceof FieldAccess){
			FieldAccess variable = (FieldAccess) lhs;
			scopes.changeValue(variable.getName().getIdentifier(), tunnel(node.getRightHandSide()));			 
	      }
		}
	}
	
	private ASTNode tunnel(ASTNode exp){
		if (exp instanceof SimpleName){
			SimpleName variable = (SimpleName) exp;
			ASTNode value = scopes.getValue(variable.getIdentifier());
			return value;
		} else
		if (exp instanceof FieldAccess){
			FieldAccess variable = (FieldAccess) exp;
			return scopes.getTDValue(variable.getName().getIdentifier());			
		}
		
		return exp;
	}	
	
	protected LexicalizedNonTerminal lMethodNonTerminal(MethodInvocation method) {
		MethodInfo info = new MethodInfo(method.getName().getIdentifier(), method.arguments().size());
		
		info.setUserDef(method.getExpression() == null || method.getExpression() instanceof ThisExpression);
		
		return lNonTerminal0(method, info);
	}	

	protected LexicalizedNonTerminal lPrefixExpressionNonTerminal(PrefixExpression node) {
		return lNonTerminal0(node, new PrefixExpressionInfo(node.getOperator().toString()));
	}

	protected LexicalizedNonTerminal lPostfixExpressionNonTerminal(PostfixExpression node) {
		return lNonTerminal0(node, new PostfixExpressionInfo(node.getOperator().toString()));
	}

	protected LexicalizedNonTerminal lClassInstanceCreationNonTerminal(ClassInstanceCreation node) {
		return lNonTerminal0(node, new ClassInstanceCreationInfo(node.getType().toString(), node.typeArguments(), node.arguments().size()));
	}

	protected LexicalizedNonTerminal lInfixExpressionNonTerminal(InfixExpression node) {
		return lNonTerminal0(node, new InfixExpressionInfo(node.getOperator().toString()));
	}

	protected LexicalizedNonTerminal lArrayCreationNonTerminal(ArrayCreation node) {
		return lNonTerminal0(node, new ArrayCreationInfo(node.getType().toString(), node.dimensions().size()));
	}	
	
    protected LexicalizedNonTerminal lNonTerminal0(ASTNode node, LexicalizedInfo info){
    	if (this.scopes != null){   		
    		if(this.scopes.contains(info.getName())){
    			info.setUserDef(true);
    		}
    	}
    	return Config.getFactory().getLexicalizedNonTerminal(node, info);
    }

	public LexicalizedNonTerminal getResult() {
		LexicalizedNonTerminal result1 = result;
		result = null;
		return result1;
	}

	public void setResult(LexicalizedNonTerminal result) {
		this.result = result;
	}
}

class LNonTerminalBasicBuilder extends LNonTerminalBuilder{

	public LNonTerminalBasicBuilder(SimpleScopes scopes) {
		super(scopes);
		// TODO Auto-generated constructor stub
	}
	
	protected LexicalizedNonTerminal lSimpleNameNonTerminal(SimpleName name) {
		return lNonTerminal0(name, new SimpleNameInfo(name.getIdentifier()));
	}

}


abstract class LNonTerminalBuilder extends FalseBuilder {
	
	protected SimpleScopes scopes;
	private LexicalizedNonTerminal result;		
	
	public LNonTerminalBuilder(SimpleScopes scopes) {
		this.scopes = scopes;
	}
	
	public boolean visit(MethodInvocation node){
		setResult(lMethodNonTerminal(node));
		return false;
	}
	
	public boolean visit(FieldAccess node){
		setResult(lFieldNonTerminal(node));
		return false;		
	}
	
	public boolean visit(SimpleName node){
		setResult(lSimpleNameNonTerminal(node));
		return false;
	}
	
	public boolean visit(ArrayCreation node){
		setResult(lArrayCreationNonTerminal(node));
		return false;
	}
	
	public boolean visit(ClassInstanceCreation node){
		setResult(lClassInstanceCreationNonTerminal(node));
		return false;
	}
	
	public boolean visit(InfixExpression node){
		setResult(lInfixExpressionNonTerminal(node));
		return false;
	}
	
	public boolean visit(PostfixExpression node){
		setResult(lPostfixExpressionNonTerminal(node));
		return false;
	}
	
	public boolean visit(PrefixExpression node){
		setResult(lPrefixExpressionNonTerminal(node));
		return false;
	}
	
	public boolean visit(SuperMethodInvocation node){
		setResult(lSuperMethodNonTerminal(node));
		return false;
	}
	
	public boolean visit(SuperFieldAccess node){
		setResult(lSuperFieldNonTerminal(node));
		return false;
	}
	
	protected abstract LexicalizedNonTerminal lSimpleNameNonTerminal(SimpleName name);
	
    protected LexicalizedNonTerminal lSuperFieldNonTerminal(SuperFieldAccess field) {
		FieldInfo info = new FieldInfo(field.getName().getIdentifier());
		info.setUserDef(true);
		return lNonTerminal0(field, info);
	}

	protected LexicalizedNonTerminal lSuperMethodNonTerminal(SuperMethodInvocation method) {
		MethodInfo info = new MethodInfo(method.getName().getIdentifier(), method.arguments().size());
		info.setUserDef(true);
		return lNonTerminal0(method, info);
	}

	protected LexicalizedNonTerminal lPrefixExpressionNonTerminal(PrefixExpression node) {
		return lNonTerminal0(node, new PrefixExpressionInfo(node.getOperator().toString()));
	}

	protected LexicalizedNonTerminal lPostfixExpressionNonTerminal(PostfixExpression node) {
		return lNonTerminal0(node, new PostfixExpressionInfo(node.getOperator().toString()));
	}

	protected LexicalizedNonTerminal lClassInstanceCreationNonTerminal(ClassInstanceCreation node) {
		return lNonTerminal0(node, new ClassInstanceCreationInfo(node.getType().toString(), node.typeArguments(), node.arguments().size()));
	}

	protected LexicalizedNonTerminal lInfixExpressionNonTerminal(InfixExpression node) {
		return lNonTerminal0(node, new InfixExpressionInfo(node.getOperator().toString()));
	}

	protected LexicalizedNonTerminal lArrayCreationNonTerminal(ArrayCreation node) {
		return lNonTerminal0(node, new ArrayCreationInfo(node.getType().toString(), node.dimensions().size()));
	}

	protected LexicalizedNonTerminal lFieldNonTerminal(FieldAccess field) {
		FieldInfo info =  new FieldInfo(field.getName().getIdentifier());
		
		info.setUserDef(field.getExpression() == null || field.getExpression() instanceof ThisExpression);
		
		return lNonTerminal0(field, info);
	}

	protected LexicalizedNonTerminal lMethodNonTerminal(MethodInvocation method) {
		MethodInfo info = new MethodInfo(method.getName().getIdentifier(), method.arguments().size());
		
		info.setUserDef(method.getExpression() == null || method.getExpression() instanceof ThisExpression);
		
		return lNonTerminal0(method, info);
	}
    
    protected LexicalizedNonTerminal lNonTerminal0(ASTNode node, LexicalizedInfo info){
    	if (this.scopes != null){   		
    		if(this.scopes.contains(info.getName())){
    			info.setUserDef(true);
    		}    		
    	}
    	return Config.getFactory().getLexicalizedNonTerminal(node, info);
    }

	public LexicalizedNonTerminal getResult() {
		return result;
	}

	public void setResult(LexicalizedNonTerminal result) {
		this.result = result;
	}	
	
}