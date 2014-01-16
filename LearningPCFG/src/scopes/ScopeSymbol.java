package scopes;

import symbol.Symbol;

public class ScopeSymbol extends Symbol {

	private String token;
	
	public ScopeSymbol(String token){
		assert token != null;
		this.token = token;
	}

	public String toString(){
		return token;
	}
	
	public int hashCode(){
		return token.hashCode()^758237854;
	}
	
}
