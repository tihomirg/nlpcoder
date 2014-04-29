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
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import api.Imported;
import api.StabileAPI;
import builders.SingleNodeVisitor;
import builders.IBuilder;
import scopes.NameScopes;
import scopes.ScopeKeyValue;
import scopes.SimpleEvalScopes;
import sequences.one.builders.ExpressionBuilder;
import sequences.one.exprs.Expr;
import statistics.SequenceStatistics;

public class SequenceBuilder extends SingleNodeVisitor implements IBuilder {

	private SequenceStatistics statistics;
	private NameScopes methods;
	private NameScopes fields;
	private Imported imported;
	private SimpleEvalScopes locals;
	private NameScopes params;
	private StabileAPI api;
	private ExpressionBuilder expBuilder;

	public SequenceBuilder(StabileAPI api) {
		this.statistics  = new SequenceStatistics();
		this.methods = new NameScopes();
		this.fields = new NameScopes();
		this.locals =  new SimpleEvalScopes();
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

		popAndSaveLocals();

		return false;
	}

	private void popAndSaveLocals() {
		ScopeKeyValue<String, List<String>> variables = locals.peek();	
		statistics.inc(variables);
		locals.pop();
	}

	public boolean visit(ExpressionStatement node) {
		return true;
	}	
	
	public boolean visit(IfStatement node){
		Expression exp = node.getExpression();
		if(exp != null){
			locals.pushLocation("if("+expBuilder.getExpr(exp)+")");
			
			//TODO:
			//1) transform expr into VNF
			//2) transform into pattern and list of subs
			
			locals.popLocation();
		}
		
		Statement thenStatement = node.getThenStatement();
		if (thenStatement != null) {
			locals.pushLocation("if then");
			locals.push();
			thenStatement.accept(this);
			popAndSaveLocals();
			locals.popLocation();
		}
		
		Statement elseStatement = node.getElseStatement();
		if(elseStatement != null){
			locals.pushLocation("if else");		
			locals.push();
			elseStatement.accept(this);
			popAndSaveLocals();
			locals.popLocation();
		}
		
		return false;
	}
	
	public boolean visit(EnhancedForStatement node) {
		Expression exp = node.getExpression();
		if(exp != null){
			locals.pushLocation("forE("+expBuilder.getExpr(exp)+")");

			//TODO:
			//1) transform expr into VNF
			//2) transform into pattern and list of subs			
			
			locals.popLocation();
		}
		
		locals.push();
		SingleVariableDeclaration parameter = node.getParameter();
		if (parameter != null) {
			locals.pushLocation("forE param");
			parameter.accept(this);	
			locals.popLocation();
		}
		
		Statement body = node.getBody();
		if (body != null) {
			locals.pushLocation("forE body");
			body.accept(this);
			locals.popLocation();	
		}

		popAndSaveLocals();
		return false;
	}

	public boolean visit(ForStatement node){
		Expression exp = node.getExpression();
		if(exp != null){
			locals.pushLocation("for("+expBuilder.getExpr(exp)+")");

			locals.popLocation();
		}
		
		locals.push();
		locals.pushLocation("for init");
		accept(node.initializers());
		locals.popLocation();
		
		locals.pushLocation("for update");
		accept(node.updaters());
		locals.popLocation();
		
		Statement body = node.getBody();
		if (body != null) {
			locals.pushLocation("for body");
			body.accept(this);
			locals.popLocation();	
		}
		
		popAndSaveLocals();
		return false;
	}
	
	public boolean visit(WhileStatement node){
		Expression exp = node.getExpression();
		if(exp != null){
			locals.pushLocation("while("+expBuilder.getExpr(exp)+")");
			locals.popLocation();
		}
		
		locals.push();
		
		Statement body = node.getBody();
		if (body != null) {
			locals.pushLocation("while body");
			body.accept(this);
			locals.popLocation();	
		}
		
		popAndSaveLocals();
		return false;
	}

	private void accept(List<ASTNode> initializers) {
		for (ASTNode node : initializers) {
			node.accept(this);
		}
	}

	public boolean visit(SynchronizedStatement node) {
		return true;
	}

	public boolean visit(ThrowStatement node) {
		return true;
	}

	public boolean visit(TryStatement node) {
		return true;
	}

	//------------------------------------------------------ Special ------------------------------------------------------	

	public boolean visit(SimpleName node){
		String variable = node.getIdentifier();

		if (!isParam(variable)){
			if (isLocal(variable)){
				List<String> sequence = locals.get(variable);
				sequence.add(locals.locationToString()+" "+variable);
			}
		}
		
		return false;
	}

	private boolean isParam(String variable) {
		return params.contains(variable);
	}

	public boolean visit(QualifiedName node) {
		return false;
	}	

	//This is where variables are born
	public boolean visit(VariableDeclarationFragment node) {
		String name = node.getName().getIdentifier();

		ASTNode parent = node.getParent();
		String type = "";
		if (parent instanceof VariableDeclarationStatement){
			VariableDeclarationStatement vds = (VariableDeclarationStatement) parent;
			
			type = getTypeName(vds.getType())+" = ";
			
		}
		
		List<String> list = new LinkedList<String>();

		Expression initializer = node.getInitializer();
		if (initializer != null){

			if (initializer instanceof ClassInstanceCreation){
				ClassInstanceCreation clazz = (ClassInstanceCreation) initializer;
				String typeName = getTypeName(clazz.getType()); 
				List<ASTNode> arguments = clazz.arguments();
				int argNum = arguments.size();
				if(isImportedCons(typeName, argNum)){
					list.add(type+"new "+typeName+"("+argNum+")");
				}  else list.add(null); 

			} else {
				if (initializer instanceof MethodInvocation){
					MethodInvocation method = (MethodInvocation) initializer;	
					String methodName = method.getName().getIdentifier();
					int argNum = method.arguments().size();
					if(!isOwnerMethod(methodName)){
						if(isImportedMethod(methodName, argNum)){
							list.add(type+methodName+"("+argNum+")");
						} else list.add(null);
					} else list.add(null);
				} else if (initializer instanceof FieldAccess){
					FieldAccess filed = (FieldAccess) initializer;
					String fieldName = filed.getName().getIdentifier();
					if(!isOwnerField(fieldName)){
						if(isImportedField(fieldName)){
							list.add(type+fieldName);
						} else list.add(null);
					} else list.add(null);

				} else list.add(null);
			}
		} else {
			list.add(null);
		}

		locals.put(name, list);

		return false;
	}

	//-------------------------------------------------------  Rest --------------------------------------------------------	

	public boolean visit(CatchClause node) {
		return true;
	}

	public boolean visit(CompilationUnit node) {
		this.imported = api.createImported();
		this.expBuilder = new ExpressionBuilder(this.imported, api.getStf());
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

	//-------------------------------------- auxiliary methods ---------------------------------------------
	
	private String getTypeName(Type type) {
		if (type.isParameterizedType()) {
			ParameterizedType paramType = (ParameterizedType) type;
			return paramType.getType().toString();
		} else if (type.isArrayType()) {
			ArrayType arrayType = (ArrayType) type;
			return arrayType.getElementType().toString();
		} else return type.toString();
	}

	private boolean isImportedCons(String name, int argNum) {
		return imported.isImporteddConstructor(name, argNum);
	}

	private boolean isImportedField(String name) {
		return imported.isImportedField(name);
	}

	private boolean isOwnerField(String name) {
		return fields.contains(name);
	}

	private boolean isLocal(String name) {
		return locals.contains(name);
	}

	private boolean isImportedMethod(String name, int argNum) {
		return imported.isImportedMethod(name, argNum);
	}

	private boolean isOwnerMethod(String name) {
		return methods.contains(name);
	}	
}
