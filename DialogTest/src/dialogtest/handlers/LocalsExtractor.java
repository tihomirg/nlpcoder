package dialogtest.handlers;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import types.StabileTypeFactory;
import api.Imported;
import api.Local;
import api.StabileAPI;


public class LocalsExtractor extends ASTVisitor {
	
	private int position;
	private Locals locals;
	private TypeBuilder typeBuilder;
	private StabileAPI api;
	private Imported imported;
	
	public LocalsExtractor(int position, StabileAPI api) {
		this.position = position;
		this.locals = new Locals();
		this.api = api;
	}

	private boolean isPositionBetween(int startPosition, int endPosition) {
		return position >= startPosition && position <= endPosition;
	}
	
	public Locals getLocals() {
		return locals;
	}

	public boolean visit(CompilationUnit node) {
		this.imported = api.createImported();
		StabileTypeFactory stf = api.getStf();
		this.typeBuilder = new TypeBuilder(stf, imported);
		return true;
	}
	
	public boolean visit(ImportDeclaration node) {
		api.load(imported, node.getName().toString(), node.isOnDemand());
		return false;
	}	
	
	//------------------------------------------------------ Statements ------------------------------------------------------	

	public boolean visit(Block block) {
		locals.push();
		List<ASTNode> statements = block.statements();
		for (ASTNode statement : statements) {
			int startPosition = statement.getStartPosition();
			int length = statement.getLength();
			int endPosition = startPosition + length;
			
			System.out.println(statement+" type = "+statement.getClass().getName()+" s.p = "+startPosition+" e.p = "+endPosition);
			
			if (isPositionGreaterThen(startPosition)){
				if (!isPositionBetween(startPosition, endPosition)){
					statement.accept(this);
				} else {
					if (!(statement instanceof VariableDeclarationStatement)){
						statement.accept(this);
					} else {
						this.locals.localized();
						break;
					}
				}
			} else {
				this.locals.localized();
				break;
			}
		}
		
		int startPosition = block.getStartPosition();
		int length = block.getLength();
		int endPosition = startPosition + length;

		if (!isPositionGreaterThen(endPosition)){
			this.locals.localized();
		}
		
		locals.pop();
		return false;
	}

	private boolean isPositionGreaterThen(int nextStartPosition) {
		return position >= nextStartPosition;
	}
	
	public boolean visit(EnhancedForStatement node) {
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
	
	public boolean visit(MethodDeclaration node){
		locals.push();
		return true;
	}

	public void endVisit(MethodDeclaration node){
		locals.pop();
	}	
	
	public boolean visit(TypeDeclaration node) {
		MethodDeclaration[] methodDecls = node.getMethods();

		for (MethodDeclaration methodDecl : methodDecls) {
			methodDecl.accept(this);
		}
		
		return false;
	}	

	private void accept(List<ASTNode> initializers) {
		for (ASTNode node : initializers) {
			node.accept(this);
		}
	}
	
	public boolean visit(VariableDeclarationStatement node){
		List<ASTNode> fragments = node.fragments();
		
		for (ASTNode astNode : fragments) {
			if (astNode instanceof VariableDeclarationFragment){
				VariableDeclarationFragment fragment = (VariableDeclarationFragment) astNode;

				String name = fragment.getName().getIdentifier();
				
				int dimensions = fragment.getExtraDimensions();
				types.Type type = typeBuilder.createArrayType(node.getType(), dimensions);
				
				locals.add(new Local(name, type));						
			}
		}
		
		return false;
	}
	
	public boolean visit(VariableDeclarationExpression node){
		List<ASTNode> fragments = node.fragments();
		
		for (ASTNode astNode : fragments) {
			if (astNode instanceof VariableDeclarationFragment){
				VariableDeclarationFragment fragment = (VariableDeclarationFragment) astNode;
				
				String name = fragment.getName().getIdentifier();
				
				int dimensions = fragment.getExtraDimensions();
				types.Type type = typeBuilder.createArrayType(node.getType(), dimensions);
				
				locals.add(new Local(name, type));				
			}
		}
		
		return false;
	}	
}
