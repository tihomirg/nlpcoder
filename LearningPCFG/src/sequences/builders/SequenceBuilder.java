package sequences.builders;

import java.io.PrintStream;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;

import builders.IBuilder;

import scopes.NameScopes;
import scopes.SimpleEvalScopes;
import selection.types.Type;
import sequences.builders.SequenceBuilder.TempExpression;
import sequences.builders.SequenceBuilder.TempVariable;
import statistics.SequenceStatistics;
import declarations.API;
import declarations.Imported;

public class SequenceBuilder extends IBuilder {

	public class TempExpression {

	}

	public static class TempVariable {

		public boolean isVariable() {
			// TODO Auto-generated method stub
			return false;
		}

		public Type getType() {
			// TODO Auto-generated method stub
			return null;
		}

		public boolean isTemp() {
			// TODO Auto-generated method stub
			return false;
		}

	}

	private NameScopes methods;
	private NameScopes fields;
	private Imported imported;
	private SimpleEvalScopes locals;
	private NameScopes params;
	private API api;
	private TempVariable tempVariable;
	private TempExpression tempExpression;
	
	
	@Override
	public void print(PrintStream out) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void releaseUnder(int percent) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean visit(MethodInvocation node) {
		String name = node.getName().getIdentifier();
		List<ASTNode> arguments = node.arguments();
		int argNum = arguments.size();

		if(!isOwnerMethod(name)){
			if(isImportedMethod(name, argNum)){
				
				TempVariable tv = getTempVariable(node.getExpression());
				
				if (tv.isVariable()) {

					
					TempExpression[] tempExprs = new TempExpression[arguments.size()];
					for (int i= 0; i < arguments.size(); i++){
						tempExprs[i] = getTempExpression(arguments.get(i)); //Or again TempVariable
					}
					
					if (!tv.isTemp()){
						//TODO: Get local variable, use it to update the expression
						
						
					} else {
						Type type = tv.getType();				
					}
					
					//TODO: store the expression into the  
					
				} else {
					
				}
			}
		}
		
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

	private TempExpression getTempExpression(ASTNode argument) {
		// TODO Auto-generated method stub
		return tempExpression;
	}

	private TempVariable getTempVariable(Expression expression) {
		// TODO Auto-generated method stub		
		return tempVariable;
	}

	private boolean isLocal(String variable) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isImportedMethod(String name, int argNum) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isOwnerMethod(String name) {
		// TODO Auto-generated method stub
		return false;
	}	
	

}
