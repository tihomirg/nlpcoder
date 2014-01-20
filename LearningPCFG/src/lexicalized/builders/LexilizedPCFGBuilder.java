package lexicalized.builders;

import lexicalized.ast.LexicalizedSimpleName;
import lexicalized.info.LexicalizedInfo;
import lexicalized.info.MethodInfo;
import lexicalized.rules.LexicalizedMethodInvocationRule;


import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;

import builders.PCFGBuilder;

import rules.MethodInvocationRule;
import symbol.Terminals;

public class LexilizedPCFGBuilder extends PCFGBuilder {
	
	public boolean visit(MethodInvocation node) {
		statistics.inc(new LexicalizedMethodInvocationRule(node));
		
		ASTNode exp = node.getExpression();
		if(exp != null) exp.accept(this);
		
		java.util.List args = node.arguments();
		visit(args);	
		
		visit(node.typeArguments());	
		
		return false;
	}
	
	public void visit(java.util.List<ASTNode> nodes){
	  if (nodes != null){
		  for(ASTNode node: nodes){
			  node.accept(this);
		  }
	  }
	}	
}
