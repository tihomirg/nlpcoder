package symbol;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InfixExpression.Operator;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;

import definitions.Declaration;

import selection.types.TypeFactory;
import symbol.temp.DeclarationSet;

public class Factory {
    private final Symbol NULL = new Null();
	private final Symbol HOLE = new Hole();
    private final Symbol TRUE;
    private final Symbol FALSE;
    
	private static final Map<String, Symbol> numberMap = new HashMap<String, Symbol>();
	private static final Map<Character, Symbol> charMap = new HashMap<Character, Symbol>();
	private static final Map<String, Symbol> stringMap = new HashMap<String, Symbol>();
    
	private TypeFactory factory;
	
	public Factory(TypeFactory factory){
		this.factory = factory;
		TRUE = new BooleanLiteral(true, factory);
		FALSE = new BooleanLiteral(false, factory);
	}
	
    public Symbol createHole(){
    	return HOLE;
    }

	public Method createConstructor(Declaration decl){
		return new Method(decl);
	}
	
	public Method createMethod(Declaration decl){
		return new Method(decl);
	}
	
	public Field createField(Declaration decl){
		return new Field(decl);
	}
	
	public Method createMethod(Declaration decl, Symbol receiver, Symbol[] arguments){
		return new Method(decl, receiver, arguments);
	}
	
	public Method createConstructor(Declaration decl, Symbol receiver, Symbol[] arguments){
		return new Method(decl, receiver, arguments);
	}
	
	public Field createField(Declaration decl, Symbol receiver){
		return new Field(decl, receiver);
	}
	
	public DeclarationSet createDeclarationSet(Set<Declaration> decls){
		return new DeclarationSet(decls);
	}

	public Symbol createBooleanLiteral(boolean value) {
		if(value) return TRUE;
		else return FALSE;
	}

	public Symbol createCharacterLiteral(char value) {
		if(!charMap.containsKey(value)){
			charMap.put(value, new CharacterLiteral(value, factory));
		}
		return charMap.get(value);
	}

	public Symbol createNumberLiteral(String value) {
		if(!numberMap.containsKey(value)){
			numberMap.put(value, new NumberLiteral(value));
		}
		return numberMap.get(value);
	}

	public Symbol createStringLiteral(String value) {
		if(!stringMap.containsKey(value)){
			stringMap.put(value, new StringLiteral(value, factory));
		}
		return stringMap.get(value);
	}
	
	public Symbol createVariable(String name){
		return new Variable(name);
	}

	public Symbol createInfixOperator(InfixExpression.Operator operator, Symbol[] operands) {
		return new InfixOperator(operator, operands);
	}

	public Symbol createPostfixOperator(PostfixExpression.Operator operator, Symbol operand) {
		return new PostfixOperator(operator, operand);
	}

	public Symbol createPrefixOperator(PrefixExpression.Operator operator, Symbol operand) {
		return new PrefixOperator(operator, operand);
	}

	public Symbol createNull() {
		return NULL;
	}
}
