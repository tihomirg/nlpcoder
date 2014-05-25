package core;

import java.util.List;

import synthesis.ExprGroup;

public abstract class GroupBuilder<T> {

	public abstract T build(List<ExprGroup> pexprs);
	
}
