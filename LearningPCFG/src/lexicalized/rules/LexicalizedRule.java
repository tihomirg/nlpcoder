package lexicalized.rules;

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
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SuperFieldAccess;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.ThisExpression;

import config.Config;

import rules.Rule;
import scopes.Scopes;
import scopes.SimpleScopes;
import symbol.Symbol;
import util.List;

public abstract class LexicalizedRule extends Rule {

	protected SimpleScopes scopes;
	
	public LexicalizedRule(ASTNode node, SimpleScopes scopes) {
		super(node);
		this.scopes = scopes;
	}
	
    protected Symbol lNonTerminal(ASTNode node){
		if (node instanceof MethodInvocation){
			return lMethodNonTerminal((MethodInvocation) node);
		} else
		if (node instanceof FieldAccess){
			return lFieldNonTerminal((FieldAccess) node);				
		} else
		if (node instanceof SimpleName){
			return lSimpleNameNonTerminal((SimpleName) node);			
		} else					
		if (node instanceof ArrayCreation){
			return lArrayCreationNonTerminal((ArrayCreation) node);
		} else
		if (node instanceof ClassInstanceCreation){
			return lClassInstanceCreationNonTerminal((ClassInstanceCreation) node);
		} else
		if (node instanceof InfixExpression){
			return lInfixExpressionNonTerminal((InfixExpression) node);
		} else 
		if (node instanceof PostfixExpression){
			return lPostfixExpressionNonTerminal((PostfixExpression) node);	
		} else
		if (node instanceof PrefixExpression){
			return lPrefixExpressionNonTerminal((PrefixExpression) node);	
		} else
		if (node instanceof SuperMethodInvocation){
			return lSuperMethodNonTerminal((SuperMethodInvocation) node);			
		} else
		if (node instanceof SuperFieldAccess){
			return lSuperFieldNonTerminal((SuperFieldAccess) node);				
		}
		
		return nonTerminal(node);
    }
    
    protected LexicalizedNonTerminal lNonTerminal2(LexicalizedNonTerminal nt){
		if (nt.isUserDef()){
			String name = nt.getInfo().getName();
			ASTNode node = scopes.getValue(name);
			
    	  if (node instanceof MethodInvocation){
			return lMethodNonTerminal((MethodInvocation) node);
		  } else				
		  if (node instanceof ArrayCreation){
			return lArrayCreationNonTerminal((ArrayCreation) node);
		  } else
		  if (node instanceof ClassInstanceCreation){
			return lClassInstanceCreationNonTerminal((ClassInstanceCreation) node);
		  } else
		  if (node instanceof InfixExpression){
			return lInfixExpressionNonTerminal((InfixExpression) node);
		  } else 
		  if (node instanceof PostfixExpression){
			return lPostfixExpressionNonTerminal((PostfixExpression) node);	
		  } else
		  if (node instanceof PrefixExpression){
			return lPrefixExpressionNonTerminal((PrefixExpression) node);	
		  }
		}
		
		return nt; //Should abstract here!
    }    
    

    protected LexicalizedNonTerminal lSuperFieldNonTerminal(SuperFieldAccess field) {
		SimpleName name = field.getName();
		return lNonTerminal0(field, new FieldInfo(name.getIdentifier()));
	}

	protected LexicalizedNonTerminal lSuperMethodNonTerminal(SuperMethodInvocation method) {
		SimpleName name = method.getName();
		return lNonTerminal0(method, new MethodInfo(name.getIdentifier(), method.arguments().size()));
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

	protected LexicalizedNonTerminal lSimpleNameNonTerminal(SimpleName name) {
		LexicalizedNonTerminal nt = lNonTerminal0(name, new SimpleNameInfo(name.getIdentifier()));
		return lNonTerminal2(nt);
	}

	protected LexicalizedNonTerminal lFieldNonTerminal(FieldAccess field) {
		SimpleName name = field.getName();
		return lNonTerminal0(field, new FieldInfo(name.getIdentifier()));
	}

	protected LexicalizedNonTerminal lMethodNonTerminal(MethodInvocation method) {
		SimpleName name = method.getName();
		return lNonTerminal0(method, new MethodInfo(name.getIdentifier(), method.arguments().size()));
	}
    
    private LexicalizedNonTerminal lNonTerminal0(ASTNode node, LexicalizedInfo info){
    	if (this.scopes != null){   		
    		if(this.scopes.contains(info.getName())){
    			info.setUserDef(true);
    		}    		
    	}
    	
    	//We assume that if a method has no receiver, it is local.
		if (node instanceof MethodInvocation){
			MethodInvocation method = (MethodInvocation) node;
			info.setUserDef(method.getExpression() == null 
					     || method.getExpression() instanceof ThisExpression);
		} else
		if (node instanceof FieldAccess){
			FieldAccess field = (FieldAccess) node;
				info.setUserDef(field.getExpression() == null 
						     || field.getExpression() instanceof ThisExpression);
		} else
		if (node instanceof SuperMethodInvocation){
			info.setUserDef(true);
		} else
		if (node instanceof SuperFieldAccess){
			info.setUserDef(true);		
		}
    	
    	return Config.getFactory().getLexicalizedNonTerminal(node, info);
    }
    
	protected List<Symbol> makeLNonTerminalList(java.util.List<ASTNode> nodes) {
		return makeLNonTerminalList(nodes.toArray(new ASTNode[0]));
	}
	
	protected List<Symbol> makeLNonTerminalList(ASTNode[] nodes) {
		List<Symbol> list = new List<Symbol>();
		for(ASTNode node: nodes){
			list.add(lNonTerminal(node));
		}
		return list;
	}
	
	public abstract boolean isUserDef();
	
}
