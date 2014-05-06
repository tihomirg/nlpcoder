package sequences;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import api.Imported;
import api.StabileAPI;
import builders.SingleNodeVisitor;
import builders.IBuilder;
import scopes.NameScopes;
import scopes.ScopeKeyValue;
import scopes.ScopesKeyValue;
import scopes.SimpleEvalScopes;
import selection.types.StabileTypeFactory;
import sequences.one.builders.ExpressionBuilder;
import sequences.one.exprs.Expr;
import statistics.CompositionStatistics;
import symbol.Symbol;
import util.Pair;

public class CompositionBuilder extends SingleNodeVisitor implements IBuilder {

	private CompositionStatistics statistics;
	private NameScopes methods;
	private NameScopes fields;
	private Imported imported;
	private ScopesKeyValue<String, Pair<String, selection.types.Type>> locals;
	private NameScopes params;
	private StabileAPI api;
	private ExpressionBuilder expBuilder;
	private TypeBuilder typeBuilder;

	public CompositionBuilder(StabileAPI api) {
		this.statistics  = new CompositionStatistics();
		this.methods = new NameScopes();
		this.fields = new NameScopes();
		this.locals = new ScopesKeyValue<String, Pair<String, selection.types.Type>>();
		this.params = new NameScopes();
		this.api = api;
	}

	public void build(CompilationUnit node){
		node.accept(this);
	}

	@Override
	public void print(PrintStream out) {
		statistics.print(out);
		//statistics.print(api.getDeclMap(), out);	
	}
	
	@Override
	public void releaseUnder(int percentage) {
		//statistics.releaseUnder(percentage);
	}

	//------------------------------------------------------ Statements ------------------------------------------------------	

	public boolean visit(Block node) {
		locals.push();

		List<ASTNode> statements = node.statements();
		for (ASTNode node2 : statements) {
			node2.accept(this);
		}

		locals.pop();
		return false;
	}

	public boolean visit(ExpressionStatement node) {
		eval(node.getExpression());
		return false;
	}
	
	public boolean visit(IfStatement node){
		Expression exp = node.getExpression();
		if(exp != null){
			eval(exp);		
		}
		
		Statement thenStatement = node.getThenStatement();
		if (thenStatement != null) {
			thenStatement.accept(this);
		}
		
		Statement elseStatement = node.getElseStatement();
		if(elseStatement != null){
			elseStatement.accept(this);
		}
		
		return false;
	}
	
	public boolean visit(EnhancedForStatement node) {
		Expression exp = node.getExpression();
		if(exp != null){
			eval(exp);
		}
		
		SingleVariableDeclaration parameter = node.getParameter();
	
		locals.push();
		parameter.accept(this);
		
		Statement body = node.getBody();
		if (body != null) {
			body.accept(this);
		}
		locals.pop();
		
		return false;
	}

	public boolean visit(ForStatement node){
		Expression exp = node.getExpression();
		if(exp != null){
			eval(exp);
		}
		
		locals.push();
		
		accept(node.initializers());
		
		Statement body = node.getBody();
		if (body != null) {
			body.accept(this);
		}
		
		accept(node.updaters());		
		
		locals.pop();
		
		return false;
	}
	
	public boolean visit(WhileStatement node){
		Expression exp = node.getExpression();
		if(exp != null){
			eval(exp);
		}
		
		Statement body = node.getBody();
		if (body != null) {
			body.accept(this);
		}
		
		return false;
	}

	private void accept(List<ASTNode> initializers) {
		for (ASTNode node : initializers) {
			node.accept(this);
		}
	}

	public boolean visit(SynchronizedStatement node) {
		eval(node.getExpression());
		return false;
	}

	public boolean visit(ThrowStatement node) {
		eval(node.getExpression());
		return false;
	}

	public boolean visit(TryStatement node) {
		return true;
	}

	//------------------------------------------------------ Special ------------------------------------------------------	

	public boolean visit(QualifiedName node) {
		return false;
	}

	public boolean visit(VariableDeclarationStatement node){
		
		List<ASTNode> fragments = node.fragments();
		
		for (ASTNode astNode : fragments) {
			if (astNode instanceof VariableDeclarationFragment){
				VariableDeclarationFragment fragment = (VariableDeclarationFragment) astNode;
				Type type = node.getType();
				selection.types.Type type2 = typeBuilder.createType(type);

				Pair value = new Pair(eval(fragment.getInitializer()), type2);
				String name = fragment.getName().getIdentifier();
				
				System.out.println(name+" = "+value);				
				
				locals.put(name, value);					
			}
		}
		
		return false;
	}
	
	//Initializer of the for statement, basically the outer expression.
	//Will come back to it when loops come into play.
	public boolean visit(VariableDeclarationExpression node){
		List<ASTNode> fragments = node.fragments();
		
		for (ASTNode astNode : fragments) {
			if (astNode instanceof VariableDeclarationFragment){
				VariableDeclarationFragment fragment = (VariableDeclarationFragment) astNode;
				Type type = node.getType();
				selection.types.Type type2 = typeBuilder.createType(type);

				Pair value = new Pair(eval(fragment.getInitializer()), type2);
				String name = fragment.getName().getIdentifier();
				
				System.out.println(name+" = "+value);				
				
				locals.put(name, value);				
			}
		}
		
		return false;
	}	

	private String eval(Expression exp) {
		Expr expr = expBuilder.getExpr(exp);
		
		if (!expr.isVariable()){
		
		List<Pair<String, String>> compos = expr.longReps();
		
		  statistics.inc(compos);
	      return compos.get(0).getFirst();
		} return expr.shortRep();
	}

	//This is where variables are born
	public boolean visit(VariableDeclarationFragment node) {
//		ASTNode parent = node.getParent();
//		if (parent instanceof VariableDeclarationStatement){
//			VariableDeclarationStatement vds = (VariableDeclarationStatement) parent;
//
//			Type type = vds.getType();
//			selection.types.Type type2 = typeBuilder.createType(type);
//			
//			String name = node.getName().getIdentifier();
//			
//			Pair value = new Pair(eval(node.getInitializer()), type2);
//			
//			System.out.println(name+" = "+value);
//			
//			locals.put(name, value);	
//		}
//		return false;		
		throw new UnsupportedOperationException();
	}

	//-------------------------------------------------------  Rest --------------------------------------------------------	

	public boolean visit(CatchClause node) {
		return true;
	}

	public boolean visit(CompilationUnit node) {
		this.imported = api.createImported();
		StabileTypeFactory stf = api.getStf();
		this.typeBuilder = new TypeBuilder(stf, imported);
		this.expBuilder = new ExpressionBuilder(imported, stf, this.typeBuilder, params, locals);
		return true;
	}

	public boolean visit(FieldDeclaration node) {
		List<VariableDeclarationFragment> fragments = node.fragments();
		for (VariableDeclarationFragment fragment : fragments) {
			Expression exp = fragment.getInitializer();
			if(exp != null)
				exp.accept(this);
		}
		return false;
	}

	public boolean visit(ImportDeclaration node) {
		String imp = node.getName().toString();
		
		System.out.println(imp);
		
		api.load(imported, imp, node.isOnDemand());
		return false;
	}

	public boolean visit(MethodDeclaration node){
		locals.push();
		params.push();
		return true;
	}

	public void endVisit(MethodDeclaration node){
		locals.pop();
		params.pop();
	}

	//TODO: Used for method parameters.
	public boolean visit(SingleVariableDeclaration node) {
		params.put(node.getName().getIdentifier());
		return false;
	}

	public boolean visit(TypeDeclaration node) {
		methods.push();
		fields.push();

		FieldDeclaration[] fieldDecls = node.getFields();

		for (FieldDeclaration fieldDecl : fieldDecls) {
			List<VariableDeclarationFragment> fragments = fieldDecl.fragments();
			for (VariableDeclarationFragment fragment : fragments) {
				fields.put(fragment.getName().getIdentifier());
			}
		}

		MethodDeclaration[] methodDecls = node.getMethods();

		for (MethodDeclaration methodDecl : methodDecls) {
			methods.put(methodDecl.getName().getIdentifier());
		}		

		for (MethodDeclaration methodDecl : methodDecls) {
			methodDecl.accept(this);
		}

		methods.pop();
		fields.pop();
		return false;
	}

	public boolean visit(TypeDeclarationStatement node) {
		return true;
	}
}
