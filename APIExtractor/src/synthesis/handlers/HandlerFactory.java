package synthesis.handlers;

public abstract class HandlerFactory {
	//Declaration handlers
	public abstract Handler getMethodInvocationHandler();
	public abstract Handler getFieldAccessHandler();
	public abstract Handler getConstructorInvocationHandler();
	
	//Literal handlers	
	public abstract Handler getBooleanLiteralHandler();
	public abstract Handler getCharacterLiteralHandler();
	public abstract Handler getNullLiteralHandler();
	public abstract Handler getNumberLiteralHandler();
	public abstract Handler getStringLiteralHandler();
	
	public abstract Handler getAssignmentHandler();
	public abstract Handler getCondExprHandler();
	
	//Type based 
	public abstract Handler getCastExprHandler();
	public abstract Handler getInstOfExprHandler();
	
	//Type based
	public abstract Handler getInfixOperatorHandler();
	public abstract Handler getPrefixOperatorHandler();
	public abstract Handler getPostfixOperatorHandler();
	
	public abstract Handler getHoleHandler();

	public abstract Handler getTypeHandler();
}
