package lexicalized.rules;

import interpreter.ENonTerminalFactory;
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

public abstract class ERule extends Rule {
	
	protected ENonTerminalFactory factory;
	
	public ERule(ASTNode node, ENonTerminalFactory factory) {
		super(node);
		this.factory = factory;
	}
	
    protected Symbol eNonTerminal(ASTNode node){
    	return this.factory.lNonTerminal(node);
    }
    
	protected List<Symbol> makeLNonTerminalList(java.util.List<ASTNode> nodes) {
		return this.factory.makeLNonTerminalList(nodes);
	}
	
	protected List<Symbol> makeLNonTerminalList(ASTNode[] nodes) {
		return this.factory.makeLNonTerminalList(nodes);
	}
	
	public abstract boolean ommit();
	
}
