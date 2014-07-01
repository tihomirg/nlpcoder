package api;

import types.Type;

public class Local {
	private Type type;
	private String name;

	public Local(String name, types.Type type) {
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public Type getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Local [name=" + name + ", type=" + type + "]";
	}
}
