package util;

import java.util.List;

import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.internal.core.dom.NaiveASTFlattener;

import scopes.NameScopes;
import scopes.SimpleEvalScopes;

public class ExpressionPrettyPrinter extends NaiveASTFlattener {

	
	private SimpleEvalScopes locals;
	private NameScopes params;
	private int count;
	
//	Expression:
//	    Annotation,
//	    ArrayAccess,
//	    ArrayCreation,
//	    ArrayInitializer,
//	    Assignment,
//	    BooleanLiteral,
//	    CastExpression,
//	    CharacterLiteral,
//	    ClassInstanceCreation,
//	    ConditionalExpression,
//	    FieldAccess,
//	    InfixExpression,
//	    InstanceofExpression,
//	    MethodInvocation,
//	    Name,
//	    NullLiteral,
//	    NumberLiteral,
//	    ParenthesizedExpression,
//	    PostfixExpression,
//	    PrefixExpression,
//	    StringLiteral,
//	    SuperFieldAccess,
//	    SuperMethodInvocation,
//	    ThisExpression,
//	    TypeLiteral,
//	    VariableDeclarationExpression
	
	public boolean visit(SimpleName node) {
		String variable = node.getIdentifier();
		
		if (!isParam(variable)){
			if (isLocal(variable)){
				List<String> sequence = locals.get(variable);
				sequence.add(locals.locationToString()+" "+variable);
			}
		}		
		
		if (locals.contains(variable)){
		  this.buffer.append(genVar());
		} else {
		  this.buffer.append(variable);
		}
		
		return false;
	}

	private boolean isLocal(String variable) {
		return locals.contains(variable);
	}

	private boolean isParam(String variable) {
	   return params.contains(variable);
    }

	private String genVar() {
	   // TODO Auto-generated method stub
	   return "v"+count;
	}

	public void reset(){
		count = 0;
	}
	
	public SimpleEvalScopes getLocals() {
		return locals;
	}

	public void setLocals(SimpleEvalScopes locals) {
		this.locals = locals;
	}

	public NameScopes getParams() {
		return params;
	}

	public void setParams(NameScopes params) {
		this.params = params;
	}
}
