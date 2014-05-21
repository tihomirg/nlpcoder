package core;

import synthesis.PartialExpression;

public abstract class GroupBuilder<T> {

	public abstract T build(PartialExpression pexpr);
	
}
