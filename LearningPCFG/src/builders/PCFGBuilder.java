package builders;
import java.io.PrintStream;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BlockComment;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EmptyStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.LineComment;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MemberRef;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.MethodRef;
import org.eclipse.jdt.core.dom.MethodRefParameter;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SuperFieldAccess;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TextElement;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.TypeParameter;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.core.dom.WildcardType;

import rules.ArrayAccessRule;
import rules.ArrayCreationRule;
import rules.ArrayInitializerRule;
import rules.ArrayTypeRule;
import rules.AssignmentRule;
import rules.BlockRule;
import rules.BooleanLiteralRule;
import rules.BreakStatementRule;
import rules.CastExpressionRule;
import rules.CatchClauseRule;
import rules.CharacterLiteralRule;
import rules.ClassInstanceCreationRule;
import rules.ConditionalExpressionRule;
import rules.ContinueStatementRule;
import rules.DoStatementRule;
import rules.EnhancedForStatementRule;
import rules.ExpressionStatementRule;
import rules.FieldAccessRule;
import rules.ForStatementRule;
import rules.IfStatementRule;
import rules.InfixExpressionRule;
import rules.InitializerRule;
import rules.InstanceofExpressionRule;
import rules.LabeledStatementRule;
import rules.MethodInvocationRule;
import rules.NullLiteralRule;
import rules.NumberLiteralRule;
import rules.ParameterizedTypeRule;
import rules.ParenthesizedExpressionRule;
import rules.PostfixExpressionRule;
import rules.PrefixExpressionRule;
import rules.PrimitiveTypeRule;
import rules.QualifiedNameRule;
import rules.QualifiedTypeRule;
import rules.ReturnStatementRule;
import rules.SimpleNameRule;
import rules.SimpleTypeRule;
import rules.SingleVariableDeclarationRule;
import rules.StringLiteralRule;
import rules.SuperFieldAccessRule;
import rules.SuperMethodInvocationRule;
import rules.SwitchCaseRule;
import rules.SwitchStatementRule;
import rules.SynchronizedStatementRule;
import rules.ThisExpressionRule;
import rules.ThrowStatementRule;
import rules.TryStatementRule;
import rules.TypeDeclarationRule;
import rules.TypeDeclarationStatementRule;
import rules.TypeLiteralRule;
import rules.TypeParameterRule;
import rules.VariableDeclarationExpressionRule;
import rules.VariableDeclarationFragmentRule;
import rules.VariableDeclarationStatementRule;
import rules.WhileStatementRules;
import rules.WildcardTypeRule;
import statistics.RuleStatisticsBase;


public class PCFGBuilder extends IBuilder {
	
	protected RuleStatisticsBase statistics = new RuleStatisticsBase();

	@Override
	public void print(PrintStream out) {
		statistics.print(out);
	}
	
	public RuleStatisticsBase getStatistics() {
		return statistics;
	}
	
	public boolean visit(ArrayAccess node) {
		statistics.inc(new ArrayAccessRule(node));
		return true;
	}
	
	public boolean visit(ArrayCreation node) {
		statistics.inc(new ArrayCreationRule(node));
		return true;
	}
	
	public boolean visit(ArrayInitializer node) {
		statistics.inc(new ArrayInitializerRule(node));
		return true;
	}
	
	public boolean visit(Assignment node) {
		statistics.inc(new AssignmentRule(node));
		return true;
	}	

	public boolean visit(ClassInstanceCreation node) {
		statistics.inc(new ClassInstanceCreationRule(node));
		return true;
	}
	
	public boolean visit(ConditionalExpression node){
		statistics.inc(new ConditionalExpressionRule(node));
		return true;
	}	
	
	public boolean visit(ExpressionStatement node) {
		statistics.inc(new ExpressionStatementRule(node));
		return true;
	}
	
	public boolean visit(FieldAccess node){
		statistics.inc(new FieldAccessRule(node));
		return true;
	}	
	
	public boolean visit(InfixExpression node){		
		statistics.inc(new InfixExpressionRule(node));
		return true;
	}
	
	public boolean visit(InstanceofExpression node) {
		statistics.inc(new InstanceofExpressionRule(node));
		return true;
	}

	public boolean visit(MethodInvocation node) {
		statistics.inc(new MethodInvocationRule(node));
		return true;
	}
	
	public boolean visit(ParenthesizedExpression node) {
		statistics.inc(new ParenthesizedExpressionRule(node));
		return true;
	}

	public boolean visit(PostfixExpression node) {
		statistics.inc(new PostfixExpressionRule(node));
		return true;
	}
	
	public boolean visit(PrefixExpression node) {
		statistics.inc(new PrefixExpressionRule(node));
		return true;
	}

	public boolean visit(SuperFieldAccess node) {
		statistics.inc(new SuperFieldAccessRule(node));
		return true;
	}	

	public boolean visit(SuperMethodInvocation node) {
		statistics.inc(new SuperMethodInvocationRule(node));
		return true;
	}	
	
	public boolean visit(VariableDeclarationExpression node){
		statistics.inc(new VariableDeclarationExpressionRule(node));
		return true;
	}

	public boolean visit(CastExpression node) {
		statistics.inc(new CastExpressionRule(node));
		return true;
	}
	
	//------------------------------------------------------ Statements ------------------------------------------------------	

	public boolean visit(Block node) {
		statistics.inc(new BlockRule(node));
		return true;
	}	

	public boolean visit(ContinueStatement node) {
		statistics.inc(new ContinueStatementRule(node));
		return true;
	}	
	
	public boolean visit(EnhancedForStatement node) {
		statistics.inc(new EnhancedForStatementRule(node));
		return true;
	}
	
	public boolean visit(DoStatement node) {
		statistics.inc(new DoStatementRule(node));
		return true;
	}	
	
	public boolean visit(ForStatement node){
		statistics.inc(new ForStatementRule(node));
		return true;
	}
	
	public boolean visit(IfStatement node){
		statistics.inc(new IfStatementRule(node));
		return true;
	}

	public boolean visit(LabeledStatement node) {
		statistics.inc(new LabeledStatementRule(node));
		return true;
	}	
	
	public boolean visit(ReturnStatement node) {
		statistics.inc(new ReturnStatementRule(node));
		return true;
	}
	
	public boolean visit(SwitchStatement node) {
		statistics.inc(new SwitchStatementRule(node));
		return true;
	}	

	public boolean visit(SwitchCase node) {
		statistics.inc(new SwitchCaseRule(node));
		return true;
	}
	
	public boolean visit(VariableDeclarationStatement node) {
		statistics.inc(new VariableDeclarationStatementRule(node));
		return true;
	}	
	
	public boolean visit(WhileStatement node) {
		statistics.inc(new WhileStatementRules(node));
		return true;
	}
	
	public boolean visit(BreakStatement node) {
		statistics.inc(new BreakStatementRule(node));
		return true;
	}

	
	//------------------------------------------------------ Literals ------------------------------------------------------	

	public boolean visit(BooleanLiteral node) {
		statistics.inc(new BooleanLiteralRule(node));
		return true;
	}
	
	public boolean visit(CharacterLiteral node) {
		statistics.inc(new CharacterLiteralRule(node));
		return true;
	}	
	
	public boolean visit(NullLiteral node){
		statistics.inc(new NullLiteralRule(node));
		return true;
	}

	public boolean visit(NumberLiteral node){
		statistics.inc(new NumberLiteralRule(node));
		return true;
	}
	
	public boolean visit(StringLiteral node) {
		statistics.inc(new StringLiteralRule(node));
		return true;
	}
	
	public boolean visit(ThisExpression node){
		statistics.inc(new ThisExpressionRule(node));
		return true;				
	}
	
	public boolean visit(ThrowStatement node) {
		statistics.inc(new ThrowStatementRule(node));
		return true;
	}
	
	public boolean visit(TryStatement node) {
		statistics.inc(new TryStatementRule(node));
		return true;
	}
	
	public boolean visit(TypeLiteral node) {
		statistics.inc(new TypeLiteralRule(node));
		return true;
	}	
	
	
	
    //------------------------------------------------------ Types ------------------------------------------------------
	
	public boolean visit(ArrayType node) {
		statistics.inc(new ArrayTypeRule(node));
		return true;
	}

	public boolean visit(ParameterizedType node) {
		statistics.inc(new ParameterizedTypeRule(node));
		return true;
	}
	
	public boolean visit(PrimitiveType node) {
		statistics.inc(new PrimitiveTypeRule(node));
		return true;
	}
	
	public boolean visit(QualifiedType node) {
		statistics.inc(new QualifiedTypeRule(node));
		return true;
	}	

	public boolean visit(SimpleType node) {
		statistics.inc(new SimpleTypeRule(node));
		return true;
	}

	public boolean visit(TypeParameter node) {
		statistics.inc(new TypeParameterRule(node));
		return true;
	}	
	
	public boolean visit(WildcardType node) {
		statistics.inc(new WildcardTypeRule(node));
		return true;
	}	
	
	//------------------------------------------------------ Special ------------------------------------------------------	
	
	public boolean visit(SimpleName node){
		statistics.inc(new SimpleNameRule(node));
		return true;
	}

	public boolean visit(QualifiedName node) {
		statistics.inc(new QualifiedNameRule(node));
		return true;
	}	
	
	public boolean visit(VariableDeclarationFragment node) {
		statistics.inc(new VariableDeclarationFragmentRule(node));
		return true;
	}	
	
	//-------------------------------------------------------  Rest --------------------------------------------------------
	
	public boolean visit(AnnotationTypeDeclaration node) {
		return false;
	}

	public boolean visit(AnnotationTypeMemberDeclaration node) {
		return false;
	}

	public boolean visit(AnonymousClassDeclaration node) {
		return false;
	}

	public boolean visit(AssertStatement node) {
		return false;
	}

	public boolean visit(BlockComment node) {
		return false;
	}
	
	public boolean visit(CatchClause node) {
		return true;
	}

	public boolean visit(CompilationUnit node) {
		return true;
	}

	public boolean visit(ConstructorInvocation node) {
		return false;
	}

	public boolean visit(EmptyStatement node) {
		return false;
	}

	public boolean visit(EnumConstantDeclaration node) {
		return false;
	}

	public boolean visit(EnumDeclaration node) {
		return false;
	}

	public boolean visit(FieldDeclaration node) {
		return false;
	}

	public boolean visit(ImportDeclaration node) {
		return false;
	}

	public boolean visit(Initializer node) {
		return false;
	}

	public boolean visit(Javadoc node) {
		return false;
	}

	public boolean visit(LineComment node) {
		return false;
	}

	public boolean visit(MarkerAnnotation node) {
		return false;
	}

	public boolean visit(MemberRef node) {
		return false;
	}

	public boolean visit(MemberValuePair node) {
		return false;
	}

	public boolean visit(MethodRef node) {
		return false;
	}

	public boolean visit(MethodRefParameter node) {
		return false;
	}

	//TODO: See what to do with parameters.
	public boolean visit(MethodDeclaration node){
		ASTNode body = node.getBody();
		if(body != null) body.accept(this);
		
		return false;
	}

	public boolean visit(Modifier node) {
		return false;
	}

	public boolean visit(NormalAnnotation node) {
		return false;
	}

	public boolean visit(PackageDeclaration node) {
		return false;
	}

	public boolean visit(SingleMemberAnnotation node) {
		return false;
	}

	//TODO: Used for method parameters.
	public boolean visit(SingleVariableDeclaration node) {
		return false;
	}

	public boolean visit(SuperConstructorInvocation node) {
		return false;
	}

	public boolean visit(SynchronizedStatement node) {
		return true;
	}

	public boolean visit(TagElement node) {
		return false;
	}

	public boolean visit(TextElement node) {
		return false;
	}

	public boolean visit(TypeDeclaration node) {
		return true;
	}

	public boolean visit(TypeDeclarationStatement node) {
		return true;
	}

}
