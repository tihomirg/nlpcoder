package core;

import synthesis.ExprGroup;

public abstract class GroupBuilder<T> {

	public abstract T build(ExprGroup pexprs);
	
}
