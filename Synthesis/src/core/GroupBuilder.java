package core;

import java.util.List;

import synthesis.PartialExpression;

public abstract class GroupBuilder<T> {

	public abstract T build(List<PartialExpression> pexprs);
	
}
