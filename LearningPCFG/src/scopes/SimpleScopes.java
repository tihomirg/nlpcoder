package scopes;

import org.eclipse.jdt.core.dom.ASTNode;

import symbol.ValueScopeSymbol;

public class SimpleScopes extends Scopes<ValueScopeSymbol> {

	public void add(String name){
		add(new ValueScopeSymbol(name));
	}

	public void add(String name, ASTNode value){
		add(new ValueScopeSymbol(name, value));
	}	
	
	public boolean contains(String name){
		return contains(new ValueScopeSymbol(name));
	}
	
	public ValueScopeSymbol get(String name){
		return get(new ValueScopeSymbol(name));
	}
	
	public ValueScopeSymbol getTD(String name){
		return getTD(new ValueScopeSymbol(name));
	}
	
	public ASTNode getValue(String name){
		ValueScopeSymbol variable = get(new ValueScopeSymbol(name));
		return variable != null ? variable.getValue() : null;
	}
	
	public ASTNode getTDValue(String name){
		ValueScopeSymbol variable = getTD(new ValueScopeSymbol(name));
		return variable != null ? variable.getValue() : null;
	}	
	
	public void changeValue(String name, ASTNode value){
		ValueScopeSymbol symbol = get(name);
		if (symbol!= null) symbol.setValue(value);
	}

	public void changeValueTD(String name, ASTNode value){
		ValueScopeSymbol symbol = getTD(name);
		if (symbol != null) symbol.setValue(value);
	}
	
}
