package builders;


import java.io.PrintStream;
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
import org.eclipse.jdt.core.dom.WildcardType;
import api.Imported;
import api.StabileAPI;
import definitions.Declaration;
import scopes.NameScopes;
import statistics.DeclCountStatistics;

public class DeclFreqBuilder extends ASTVisitor implements IBuilder {
	
	private DeclCountStatistics statistics;
	private NameScopes methods;
	private NameScopes fields;
	private Imported imported;
	private StabileAPI api;
	
	public DeclFreqBuilder(StabileAPI api2) {
		this.statistics  = new DeclCountStatistics();
		this.methods = new NameScopes();
		this.fields = new NameScopes();
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
		String name = getTypeName(node.getType()); 
		List<ASTNode> arguments = node.arguments();
		int argNum = arguments.size();
		if(isImportedCons(name, argNum)){
			statistics.inc(getImportedConstructors(name, argNum));
		}
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
			   statistics.inc(getImportedFields(name));
			}
		}
		return true;
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
			   statistics.inc(getImportedMethods(name, argNum));
			}
		}
		return true;
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
		return true;
	}
	
	public boolean visit(EnhancedForStatement node) {		
		return true;
	}
	
	public boolean visit(ForStatement node){
		return true;
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
		return false;
	}

	public boolean visit(QualifiedName node) {
		return false;
	}	
	
	//This is where variables are born
	public boolean visit(VariableDeclarationFragment node) {
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
		return true;
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
	    methods.push();
	    fields.push();
	    
	    MethodDeclaration[] methodDecls = node.getMethods();
	    
	    for (MethodDeclaration methodDecl : methodDecls) {
			methods.put(methodDecl.getName().getIdentifier());
		}
	    
	    FieldDeclaration[] fieldDecls = node.getFields();
	    
	    for (FieldDeclaration fieldDecl : fieldDecls) {
	    	List<VariableDeclarationFragment> fragments = fieldDecl.fragments();
	    	for (VariableDeclarationFragment fragment : fragments) {
				fields.put(fragment.getName().getIdentifier());
			}
		}
	    
		return true;
	}
	
	public void endVisit(TypeDeclaration node) {
		methods.pop();
		fields.pop();
	}
	
	public boolean visit(TypeDeclarationStatement node) {
		return true;
	}

	public boolean visit(ArrayAccess node) {
		return true;
	}
	
	public boolean visit(ArrayCreation node) {
		return true;
	}
	
	public boolean visit(ArrayInitializer node) {
		return true;
	}	
	
	@Override
	public void releaseUnder(int percentage) {
		//statistics.releaseUnder(percentage);
	}
}
