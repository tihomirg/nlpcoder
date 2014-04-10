package sequences.trees;

import selection.types.Type;

public class StaticFieldAccess extends Expression {

	private String name;
	private String className;
	
	public StaticFieldAccess(String name, String className, Type type) {
		super(type);
		this.name = name;
		this.className = className;
	}
	
	
	
}
