package merging.core;

import synthesis.handlers.DeclarationHandler;
import synthesis.handlers.Handler;
import synthesis.handlers.HandlerFactory;
import synthesis.handlers.LiteralHandler;
import synthesis.handlers.LocalsHandler;
import synthesis.handlers.OperatorHandler;
import synthesis.handlers.SingleHandler;
import synthesis.handlers.SingleTypedHandler;
import synthesis.handlers.TypeHandler;
import synthesis.handlers.TypedOperatorHandler;

public class HandlerTable extends HandlerFactory {
	
	private Handler methodInvocationHandler = new DeclarationHandler();
	private Handler fieldAccessHandler = new DeclarationHandler();
	private Handler constructorInvocationHandler = new DeclarationHandler();
	
	private Handler booleanLiteralHandler = new LiteralHandler();
	private Handler characterLiteralHandler = new LiteralHandler();
	private Handler nullLiteralHandler = new LiteralHandler();
	private Handler numberLiteralHandler = new LiteralHandler();
	private Handler stringLiteralHandler = new LiteralHandler();
	
	private Handler assignmentHandler = new OperatorHandler();	
	
	private Handler castExprHandler = new SingleTypedHandler();
	private Handler instOfExprHandler = new SingleTypedHandler();	
	
	private Handler condExprHandler = new SingleHandler();
	
	private Handler infixOperatorHandler = new TypedOperatorHandler();
	private Handler prefixOperatorHandler = new TypedOperatorHandler();
	private Handler postfixOperatorHandler = new TypedOperatorHandler();	
	private Handler holeHandler = new LocalsHandler();
	private Handler typeHandler = new TypeHandler();		
	
	//Declaration handlers
	public Handler getMethodInvocationHandler() {return methodInvocationHandler;}
	public Handler getFieldAccessHandler(){return fieldAccessHandler;}
	public Handler getConstructorInvocationHandler(){return constructorInvocationHandler;}
	
	//Literal handlers	
	public Handler getBooleanLiteralHandler(){return booleanLiteralHandler;}
	public Handler getCharacterLiteralHandler() {return characterLiteralHandler;}
	public Handler getNullLiteralHandler(){return nullLiteralHandler;}
	public Handler getNumberLiteralHandler(){return numberLiteralHandler;}
	public Handler getStringLiteralHandler(){return stringLiteralHandler;}
	
	public Handler getAssignmentHandler(){return assignmentHandler;}	
	public Handler getCondExprHandler(){return condExprHandler;}
	
	//Type based 
	public Handler getCastExprHandler(){return castExprHandler;}
	public Handler getInstOfExprHandler(){return instOfExprHandler;}
	
	//Type based
	public Handler getInfixOperatorHandler(){return infixOperatorHandler;}
	public Handler getPrefixOperatorHandler(){return prefixOperatorHandler;}
	public Handler getPostfixOperatorHandler(){return postfixOperatorHandler;}
	
	public Handler getHoleHandler(){return holeHandler;}

	public Handler getTypeHandler(){return typeHandler;}
}
