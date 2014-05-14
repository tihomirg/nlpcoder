package statistics;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import statistics.handlers.Handler;
import statistics.handlers.DeclarationHandler;
import statistics.handlers.LiteralHandler;
import statistics.handlers.LocalsHandler;
import statistics.handlers.OperatorHandler;
import statistics.handlers.SingleHandler;
import statistics.handlers.SingleTypedHandler;
import statistics.handlers.TypeHandler;
import statistics.handlers.TypedOperatorHandler;
import statistics.posttrees.Expr;

public class HandlerTable {
	
	private Map<String, Handler> handlers;
	
	public HandlerTable() {
		this.handlers = new HashMap<String, Handler>();
		
		//Declaration handlers
		this.handlers.put(Names.InstanceMethodInvocation, new DeclarationHandler());
		this.handlers.put(Names.InstanceFieldAccess, new DeclarationHandler());
		this.handlers.put(Names.ConstructorInvocation, new DeclarationHandler());
		
		//Literal handlers
		this.handlers.put(Names.BooleanLiteral, new LiteralHandler());
		this.handlers.put(Names.CharacterLiteral, new LiteralHandler());
		this.handlers.put(Names.NullLiteral, new LiteralHandler());
		this.handlers.put(Names.NumberLiteral, new LiteralHandler());
		this.handlers.put(Names.StringLiteral, new LiteralHandler());
		
		this.handlers.put(Names.Assignment, new OperatorHandler());
		this.handlers.put(Names.CondExpr, new SingleHandler());
		
		//Type based 
		this.handlers.put(Names.CastExpr, new SingleTypedHandler());
		this.handlers.put(Names.InstOfExpr, new SingleTypedHandler());
		
		//Type based
		this.handlers.put(Names.InfixOperator, new TypedOperatorHandler());
		this.handlers.put(Names.PrefixOperator, new TypedOperatorHandler());
		this.handlers.put(Names.PostfixOperator, new TypedOperatorHandler());
		
		this.handlers.put(Names.Hole, new LocalsHandler());

		this.handlers.put(Names.TYPE, new TypeHandler());
		
	}
	
	public PriorityQueue<Expr> get(Expr expr){
		return handlers.get(expr.getPrefix()).handle(expr);
	}
	
	public void add(Expr expr) {
		handlers.get(expr.getPrefix()).add(expr);
	}
}
