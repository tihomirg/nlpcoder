package util;

import symbol.Symbol;

public abstract class SymbolIndex {

	protected static final int NAIVE = 0;
	protected static final int WITH_PARENT= 1;
	protected static final int WITH_GRANDAD = 2;

	protected static int type = NAIVE;	
	
	public abstract Symbol getSymbol();
}
