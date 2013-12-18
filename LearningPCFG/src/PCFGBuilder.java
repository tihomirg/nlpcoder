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

import rules.ConditionalExpressionRule;
import rules.FieldAccessRule;
import rules.ForStatementRule;
import rules.IfStatementRule;
import rules.InfixExpressionRule;
import rules.NullLiteralRule;
import rules.NumberLiteralRule;
import rules.SimpleNameRule;
import rules.ThisExpressionRule;
import rules.VariableDeclarationExpressionRule;
import statistics.RuleStatisticsBase;

public class PCFGBuilder extends ASTVisitor {
	
	private RuleStatisticsBase statistics = new RuleStatisticsBase(); 
	
	public boolean visit(InfixExpression node){			
		statistics.inc(new InfixExpressionRule(node));
		return true;
	}
	
	public boolean visit(IfStatement node){
		statistics.inc(new IfStatementRule(node));
		return true;
	}
	
	public boolean visit(NumberLiteral node){
		statistics.inc(new NumberLiteralRule(node));
		return true;
	}
	
	public boolean visit(FieldAccess node){
		statistics.inc(new FieldAccessRule(node));
		return true;
	}

	public boolean visit(ForStatement node){
		statistics.inc(new ForStatementRule(node));
		return true;
	}
	
	public boolean visit(VariableDeclarationExpression node){
		statistics.inc(new VariableDeclarationExpressionRule(node));
		return true;
	}
	
	public boolean visit(ThisExpression node){
		statistics.inc(new ThisExpressionRule(node));
		return true;				
	}
	
	public boolean visit(NullLiteral node){
		statistics.inc(new NullLiteralRule(node));
		return true;
	}
	
	public boolean visit(SimpleName node){
		statistics.inc(new SimpleNameRule(node));
		return true;
	}
	
	public boolean visit(ConditionalExpression node){
		statistics.inc(new ConditionalExpressionRule(node));
		return true;
	}

}
