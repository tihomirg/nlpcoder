import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;

import rules.FieldAccessRule;
import statistics.RuleStatisticsBase;

public class PCFGBuilder extends ASTVisitor {
	
	private RuleStatisticsBase statistics = new RuleStatisticsBase(); 
	
	public boolean visit(InfixExpression node){			
		return true;
	}
	
	public boolean visit(IfStatement node){
		return true;
	}
	
	public boolean visit(NumberLiteral node){
		return true;
	}
	
	public boolean visit(FieldAccess node){
		statistics.incCount(new FieldAccessRule(node));
		return true;
	}

	public boolean visit(ForStatement node){
		return true;
	}
	
	
	public boolean visit(VariableDeclarationExpression node){
		return true;
	}
	
	public boolean visit(ThisExpression node){
		return true;				
	}
	
	public boolean visit(NullLiteral node){
		return true;
	}
	
	public boolean visit(SimpleName node){				
		return true;
	}
	
	
	public boolean visit(ConditionalExpression node){			
		return true;
	}

}
