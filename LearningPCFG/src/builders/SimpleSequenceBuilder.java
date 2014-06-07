package builders;


import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.EmptyStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.Expression;
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
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SuperFieldAccess;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TextElement;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.TypeParameter;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.core.dom.WildcardType;
import api.Imported;
import api.StabileAPI;
import definitions.Declaration;
import scopes.NameScopes;
import scopes.ScopeKeyValue;
import scopes.SimpleEvalScopes;
import statistics.SequenceStatistics;


public class SimpleSequenceBuilder extends SingleNodeVisitor implements IBuilder {

	private SequenceStatistics statistics;
	private NameScopes methods;
	private NameScopes fields;
	private Imported imported;
	private SimpleEvalScopes locals;
	private NameScopes params;
	private StabileAPI api;

	public SimpleSequenceBuilder(StabileAPI api2) {
		this.statistics  = new SequenceStatistics();
		this.methods = new NameScopes();
		this.fields = new NameScopes();
		this.locals =  new SimpleEvalScopes();
		this.params = new NameScopes();
		this.api = api2;
	}
	
	public void build(CompilationUnit node){
		node.accept(this);
	}	

	@Override
	public void print(PrintStream out) {
		statistics.print(out);
		//statistics.print(api.getDeclMap(), out);	
	}

	public boolean visit(Assignment node) {
		return true;
	}

	public boolean visit(ClassInstanceCreation node) {
		//		String name = getTypeName(node.getType()); 
		//		List<ASTNode> arguments = node.arguments();
		//		int argNum = arguments.size();
		//		if(isImportedCons(name, argNum)){
		//			statistics.inc(getImportedConstructors(name, argNum));
		//		}
		return true;
	}

	private Set<Declaration> getImportedConstructors(String name, int argNum) {
		return imported.getConstructors(name, argNum);
	}

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

	public boolean visit(ConditionalExpression node){
		return true;
	}	

	public boolean visit(ExpressionStatement node) {
		return true;
	}

	public boolean visit(FieldAccess node) {
		String name = node.getName().getIdentifier();

		if(!isOwnerField(name)){
			if(isImportedField(name)){
				Expression exp = node.getExpression();

				if (exp != null){
					if (exp instanceof SimpleName){
						SimpleName receiver = (SimpleName) exp;
						String variable = receiver.getIdentifier();

						if (isLocal(variable)){
							List<String> sequence = locals.get(variable);
							sequence.add(locals.locationToString() +" "+name);
						}
					} else {
						exp.accept(this);
					}
				}
			}
		}
		
		return false;
	}

	private Set<Declaration> getImportedFields(String name) {
		return imported.getFields(name);
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

	public boolean visit(InfixExpression node){
		return true;
	}

	public boolean visit(PostfixExpression node) {
		return true;
	}

	public boolean visit(PrefixExpression node) {
		return true;
	}

	public boolean visit(MethodInvocation node) {

		String name = node.getName().getIdentifier();
		List<ASTNode> arguments = node.arguments();
		int argNum = arguments.size();

		if(!isOwnerMethod(name)){
			if(isImportedMethod(name, argNum)){

				Expression exp = node.getExpression();

				if (exp != null){
					if (exp instanceof SimpleName){
						SimpleName receiver = (SimpleName) exp;
						String variable = receiver.getIdentifier();

						if (isLocal(variable)){
							List<String> sequence = locals.get(variable);
							sequence.add(locals.locationToString()+" "+name + "("+argNum+")");
						}
					} else {
						exp.accept(this);
					}
				}
			}
		}

		for (ASTNode astNode : arguments) {
			astNode.accept(this);
		}
		
		//TODO: make this true!
		return false;
	}


	private Set<Declaration> getImportedMethods(String name, int argNum) {
		return imported.getMethods(name, argNum);
	}

	private boolean isImportedMethod(String name, int argNum) {
		return imported.isImportedMethod(name, argNum);
	}

	private boolean isOwnerMethod(String name) {
		return methods.contains(name);
	}

	public boolean visit(InstanceofExpression node) {
		return true;
	}	

	public boolean visit(ParenthesizedExpression node) {
		return true;
	}

	public boolean visit(SuperFieldAccess node) {
		return true;
	}	

	public boolean visit(SuperMethodInvocation node) {
		return true;
	}	

	public boolean visit(VariableDeclarationExpression node){
		return true;
	}

	public boolean visit(CastExpression node) {
		return true;
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

	public boolean visit(IfStatement node){
		Expression expression = node.getExpression();
		if(expression != null){
			locals.pushLocation("if("+expression+")");
			expression.accept(this);
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
		Expression expression = node.getExpression();
		if(expression != null){
			locals.pushLocation("for2("+expression+")");
			expression.accept(this);
			locals.popLocation();
		}
		
		locals.push();
		SingleVariableDeclaration parameter = node.getParameter();
		if (parameter != null) {
			locals.pushLocation("for2 param");
			parameter.accept(this);	
			locals.popLocation();
		}
		
		Statement body = node.getBody();
		if (body != null) {
			locals.pushLocation("for2 body");
			body.accept(this);
			locals.popLocation();	
		}

		popAndSaveLocals();
		return false;
	}

	public boolean visit(ForStatement node){
		Expression expression = node.getExpression();
		if(expression != null){
			locals.pushLocation("for1("+expression+")");
			expression.accept(this);
			locals.popLocation();
		}
		
		locals.push();
		locals.pushLocation("for1 init");
		accept(node.initializers());
		locals.popLocation();
		
		locals.pushLocation("for1 update");
		accept(node.updaters());
		locals.popLocation();
		
		Statement body = node.getBody();
		if (body != null) {
			locals.pushLocation("for1 body");
			body.accept(this);
			locals.popLocation();	
		}
		
		popAndSaveLocals();
		return false;
	}
	
	public boolean visit(WhileStatement node){
		Expression expression = node.getExpression();
		if(expression != null){
			locals.pushLocation("while("+expression+")");
			expression.accept(this);
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

	public boolean visit(ThisExpression node){
		return true;				
	}

	public boolean visit(ThrowStatement node) {
		return true;
	}

	public boolean visit(TryStatement node) {
		return true;
	}

	public boolean visit(TypeLiteral node) {
		return true;
	}

	//------------------------------------------------------ Literals ------------------------------------------------------	

	public boolean visit(BooleanLiteral node) {
		//statistics.inc(new BooleanLiteralRule(node));
		return true;
	}

	public boolean visit(CharacterLiteral node) {
		//statistics.inc(new CharacterLiteralRule(node));
		return true;
	}	

	public boolean visit(NullLiteral node){
		//statistics.inc(new NullLiteralRule(node));
		return true;
	}

	public boolean visit(NumberLiteral node){
		//statistics.inc(new NumberLiteralRule(node));
		return true;
	}

	public boolean visit(StringLiteral node) {
		//statistics.inc(new StringLiteralRule(node));
		return true;
	}

	//------------------------------------------------------ Types ------------------------------------------------------

	public boolean visit(ArrayType node) {
		return false;
	}

	public boolean visit(ParameterizedType node) {
		return false;
	}

	public boolean visit(PrimitiveType node) {
		return false;
	}

	public boolean visit(QualifiedType node) {
		return false;
	}	

	public boolean visit(SimpleType node) {
		return false;
	}

	public boolean visit(TypeParameter node) {
		return false;
	}	

	public boolean visit(WildcardType node) {
		return false;
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
		this.imported = api.createImported();
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

	public boolean visit(MethodDeclaration node){
		locals.push();
		params.push();
		return true;
	}

	public void endVisit(MethodDeclaration node){
		locals.pop();
		params.pop();
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
		params.put(node.getName().getIdentifier());
		return false;
	}

	public boolean visit(SuperConstructorInvocation node) {
		return false;
	}

	public boolean visit(SynchronizedStatement node) {
		return true;
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

	@Override
	public void releaseUnder(int percentage) {
		//statistics.releaseUnder(percentage);
	}

	@Override
	public void printDeclarations(PrintStream out) {
		// TODO Auto-generated method stub
		
	}
}
