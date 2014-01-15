package lexicalized.ast;

import lexicalized.info.LexicalizedInfo;

import org.eclipse.jdt.core.dom.SimpleName;

public class LexicalizedSimpleName {
	
	private SimpleName node;
	private LexicalizedInfo info;
	
	public LexicalizedSimpleName(SimpleName node, LexicalizedInfo info) {
		this.node = node;
		this.info = info;
		// TODO Auto-generated constructor stub
	}

	public SimpleName getNode() {
		return node;
	}

	public LexicalizedInfo getName() {
		return info;
	}
}
