package lexicalized.rules;

import lexicalized.info.LexicalizedInfo;
import lexicalized.info.MethodInfo;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;

import config.Config;

import rules.Rule;
import scopes.Scopes;
import symbol.Symbol;
import util.List;

public abstract class LexicalizedRule extends Rule {

	protected Scopes scopes;
	
	public LexicalizedRule(ASTNode node, Scopes scopes) {
		super(node);
		this.scopes = scopes;
	}
	
	public LexicalizedRule(ASTNode node, LexicalizedInfo info) {
		assert node != null && info != null;
		this.head = Config.getFactory().getLexicalizedNonTerminal(node, info);
	}
	
    protected Symbol lexicalizedNonTerminal(ASTNode node, LexicalizedInfo info){
    	if (this.scopes != null){
    		if(this.scopes.contains(info.getName())){
    			info.setUserDef(true);
    		}
    	}
    	return Config.getFactory().getLexicalizedNonTerminal(node, info);
    }
    
	protected List<Symbol> makeNonTerminalList(java.util.List<ASTNode> nodes) {
		return makeNonTerminalList(nodes.toArray(new ASTNode[0]));
	}
	
	protected List<Symbol> makeNonTerminalList(ASTNode[] nodes) {
		List<Symbol> list = new List<Symbol>();
		for(ASTNode node: nodes){
			if (node instanceof MethodInvocation){
				MethodInvocation method = (MethodInvocation) node;
				SimpleName name = method.getName();
				list.add(lexicalizedNonTerminal(node, new MethodInfo(name.getIdentifier(), method.arguments().size())));
			} else list.add(Config.getFactory().getNonTerminal(node));
		}
		return list;
	}    
	
}
