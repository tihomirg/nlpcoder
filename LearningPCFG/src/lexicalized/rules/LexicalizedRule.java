package lexicalized.rules;

import lexicalized.info.LexicalizedInfo;
import lexicalized.info.MethodInfo;
import lexicalized.symbol.LexicalizedSymbolFactory;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;

import rules.Rule;
import symbol.Symbol;
import symbol.SymbolFactory;
import util.List;

public abstract class LexicalizedRule extends Rule {

	public LexicalizedRule(ASTNode node) {
		super(node);
	}
	
	public LexicalizedRule(ASTNode node, LexicalizedInfo info) {
		assert node != null && info != null;
		this.head = LexicalizedSymbolFactory.getLexicalizedNonTerminal(node, info);
	}
	
    protected static Symbol lexicalizedNonTerminal(ASTNode node, LexicalizedInfo info){
    	return LexicalizedSymbolFactory.getLexicalizedNonTerminal(node, info);
    }	
    
	protected static List<Symbol> makeNonTerminalList(java.util.List<ASTNode> nodes) {
		List<Symbol> list = new List<Symbol>();
		for(ASTNode node: nodes){
			if (node instanceof MethodInvocation){
				MethodInvocation method = (MethodInvocation) node;
				SimpleName name = method.getName();
				list.add(LexicalizedSymbolFactory.getLexicalizedNonTerminal(node, new MethodInfo(name.getIdentifier(), method.arguments().size())));
			} else list.add(SymbolFactory.getNonTerminal(node));
		}
		return list;
	}
	
	protected static List<Symbol> makeNonTerminalList(ASTNode[] nodes) {
		List<Symbol> list = new List<Symbol>();
		for(ASTNode node: nodes){
			if (node instanceof MethodInvocation){
				MethodInvocation method = (MethodInvocation) node;
				SimpleName name = method.getName();
				list.add(LexicalizedSymbolFactory.getLexicalizedNonTerminal(node, new MethodInfo(name.getIdentifier(), method.arguments().size())));
			} else list.add(SymbolFactory.getNonTerminal(node));
		}
		return list;
	}    
	
}
