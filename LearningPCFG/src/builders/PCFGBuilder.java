package builders;


import java.io.PrintStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Assignment.Operator;
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

import declarations.API;
import declarations.Imported;
import definitions.Declaration;

import scopes.NameScopes;
import scopes.EvalScopes;
import scopes.ScopesKeyValue;
import statistics.Statistics;
import symbol.Factory;
import symbol.Symbol;


public class PCFGBuilder extends IBuilder {

	private NonEvalExpBuilder nonEvaluator;
	private EvalExpBuilder evaluator;
	
	private Statistics statistics;
	private Factory factory;
	private ScopesKeyValue<String, Symbol> locals;
	private NameScopes methods;
	private NameScopes fields;
	private NameScopes params;
	private Imported imported;
	private API api;
	
	public PCFGBuilder(API api) {
		this.statistics  = new Statistics();
		this.locals = new EvalScopes();
		this.methods = new NameScopes();
		this.fields = new NameScopes();
		this.params = new NameScopes();
		this.factory = new Factory();
		this.api = api;
		this.nonEvaluator = new NonEvalExpBuilder(factory, locals, methods, fields, params);
		this.evaluator = new EvalExpBuilder(factory, locals, methods, fields, params);
	}
	
	@Override
	public void print(PrintStream out) {
		statistics.print(out);
	}
	
	public Statistics getStatistics() {
		return statistics;
	}
	
	public boolean visit(Assignment node) {
		
		if(node.getOperator().equals(Operator.ASSIGN)){
			Symbol lhs = nonEval(node.getLeftHandSide());  //TODO: This one should not be evaluated
			if (lhs.isVariable()){
				locals.put(lhs.getName(), eval(node.getRightHandSide()));  //TODO: This one should be evaluated
			}
		}
		
		return true;
	}

	private Symbol nonEval(Expression exp) {
		return nonEvaluator.getSymbol(exp);
	}

	public boolean visit(ClassInstanceCreation node) {
		String type = getTypeName(node.getType()); 
		
		if(isImportedType(type)){
			Symbol receiver = eval(node.getExpression());
			Symbol[] arguments = eval(node.arguments());

			statistics.inc(factory.createConstructor(type, receiver, arguments));
		}
		return true;
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
	
	private boolean isImportedType(String type) {
		boolean importedClass = imported.isImportedClass(type);
		System.out.println("PCFGBuilder.isImportedType(): "+type+"  "+importedClass);		
		return importedClass;
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
			if (isImportedField(name)){
			   statistics.inc(factory.createField(name, eval(node.getExpression())));
			}
		}
		
		return true;
	}
	
	private boolean isImportedField(String name) {
		boolean importedField = imported.isImportedField(name);
		System.out.println("PCFGBuilder.isImportedField(): "+name+"  "+importedField);
		return importedField;
	}

	private boolean isLocal(String name) {
		return locals.contains(name);
	}

	private boolean isOwnerField(String name) {
		return fields.contains(name);
	}

	public boolean visit(InfixExpression node){
		Symbol[] operands = eval(node.getLeftOperand(), node.getRightOperand(), node.extendedOperands());
		
		statistics.inc(factory.createInfixOperator(node.getOperator(), operands));
		return true;
	}
	
	private Symbol[] eval(Expression leftOperand, Expression rightOperand, List<ASTNode> extendedOperands) {
		List<ASTNode> list = new LinkedList<ASTNode>();
		
		list.add(leftOperand);
		list.add(rightOperand);
		list.addAll(extendedOperands);
		
		return eval(list);
	}

	public boolean visit(PostfixExpression node) {
		statistics.inc(factory.createPostfixOperator(node.getOperator(), eval(node.getOperand())));
		return true;
	}
	
	public boolean visit(PrefixExpression node) {
		statistics.inc(factory.createPrefixOperator(node.getOperator(), eval(node.getOperand())));
		return true;
	}

	public boolean visit(MethodInvocation node) {

		String name = node.getName().getIdentifier();
		List<ASTNode> arguments = node.arguments();
		int argNum = arguments.size();
		
		if(!isOwnerMethod(name)){
			if(isImportedMethod(name, argNum)){
			   Set<Declaration> headDecls = getImportedMethods(name, argNum);
			   Symbol receiver = eval(node.getExpression());
			   Symbol[] args = eval(arguments);
			   methodStatistics(headDecls, receiver, args);
			}
		}
		return true;
	}

	private void methodStatistics(Set<Declaration> headDecls, Symbol receiverSym, Symbol[] argSyms) {
		
		for (Declaration head : headDecls) {
			Set<Symbol> receivers = new HashSet<Symbol>();

			boolean inconsitent = false;
			
			if (receiverSym.isTemp()) {
				Set<Declaration> receiverDecls = receiverSym.getDecls();
				Set<Symbol> findRecievers = findRecievers(head, receiverDecls);
				if (findRecievers == null){
					inconsitent = true;
				} else receivers.addAll(findRecievers);
				
			} else receivers.add(receiverSym);
			
			int length = argSyms.length;
			LinkedList<Set<Symbol>> argss = new LinkedList<Set<Symbol>>();

			if (inconsitent) continue;
			
			for (int i = 0; i < length; i++) {
				Symbol argSym = argSyms[i];
				Set<Symbol> args = new HashSet<Symbol>();
				if(argSym.isTemp()){
					Set<Declaration> argDecls = argSym.getDecls();
					Set<Symbol> findArgument = findArgument(head, i, argDecls);
					if (findArgument == null) {
						inconsitent = true;
						break;
					} else {
						args.addAll(findArgument);
					}
				} else args.add(argSym);
				argss.add(args);
			}
			
			if (!inconsitent) inc(head, receivers, argss);
		}
	}
	private void inc(Declaration head, Set<Symbol> receivers, LinkedList<Set<Symbol>> argss) {
		Set<Symbol> exps = getMethods(head, receivers, argss);
		for (Symbol exp: exps) {
			statistics.inc(exp);
		}
	}

	private Set<Symbol> getMethods(Declaration head, Set<Symbol> receivers, LinkedList<Set<Symbol>> argss) {
		Set<Symbol> exps = new HashSet<Symbol>();
		for (Symbol receiver : receivers) {
			List<List<Symbol>> argSymbolss = getArguments(argss);
			for (List<Symbol> arguments: argSymbolss) {
				exps.add(factory.createMethod(head, receiver, arguments.toArray(new Symbol[arguments.size()])));
			}
		}
		return exps;
	}

	private static final List<List<Symbol>> emptyArgList = new LinkedList<List<Symbol>>(){{add(new LinkedList<Symbol>());}};
	
	private List<List<Symbol>> getArguments(LinkedList<Set<Symbol>> argss) {
		if (argss.isEmpty()){
			return emptyArgList;
		} else {
			List<List<Symbol>> result = new LinkedList<List<Symbol>>();
			Set<Symbol> firsts = argss.removeFirst();
			List<List<Symbol>> tails = getArguments(argss);
			for (Symbol first: firsts) {
				List<Symbol> list = new LinkedList<Symbol>();
				list.add(first);
				for (List<Symbol> tail : tails) {
					list.addAll(tail);
				}
				result.add(list);
			}
			return result;
		}
	}

	private Set<Symbol> findArgument(Declaration head, int i, Set<Declaration> argDecls) {
		// TODO Auto-generated method stub
		return null;
	}

	private Set<Symbol> findRecievers(Declaration head, Set<Declaration> receiverDecls) {
		// TODO Auto-generated method stub
		return null;
	}

	private Set<Declaration> getImportedMethods(String name, int argNum) {
		return imported.getMethods(name, argNum);
	}

	private boolean isImportedMethod(String name, int argNum) {
		boolean importedMethod = imported.isImportedMethod(name, argNum);
		System.out.println("PCFGBuilder.isImportedMethod() "+name+"  "+importedMethod);
		
//		Set<Declaration> methods = imported.getMethods(name);
//		for (Declaration declaration : methods) {
//			System.out.println(declaration);
//		}
		
		return importedMethod;
	}

	private boolean isOwnerMethod(String name) {
		return methods.contains(name);
	}

	private Symbol[] eval(List<ASTNode> args) {
		int length = args.size();
		Symbol[] symbols = new Symbol[length];
		
		for (int i = 0; i < length; i++) {
			symbols[i] = eval(args.get(i));
		}
		
		return symbols;
	}

	private Symbol eval(ASTNode exp) {
		if(exp != null) return evaluator.getSymbol(exp);
		else return Factory.NULL;
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
		
		locals.pop();
		
		return false;
	}
	
	public boolean visit(EnhancedForStatement node) {
		locals.push();		
		return true;
	}
	
	public void endVisit(EnhancedForStatement node) {
		locals.pop();
	}
	
	public boolean visit(ForStatement node){
		locals.push();
		return true;
	}
	
	public void endVisit(ForStatement node){
		locals.pop();
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
		locals.put(node.getName().getIdentifier(), eval(node.getInitializer()));
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
		boolean isPkg = node.isOnDemand();
		
		System.out.println("PCFGBuilder.visit(): "+imp+"  "+isPkg);
		
		api.load(imported, imp, isPkg);
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
