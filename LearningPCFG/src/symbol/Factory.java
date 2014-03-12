package symbol;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InfixExpression.Operator;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;

public class Factory {
    public static final Symbol NULL = new Null();
	public static final Symbol HOLE = new Hole();
    public static final Symbol TRUE = new BooleanLitera(true);
    public static final Symbol FALSE = new BooleanLitera(false);
    
	private static final Map<String, Symbol> numberMap = new HashMap<String, Symbol>();
	private static final Map<Character, Symbol> charMap = new HashMap<Character, Symbol>();
	private static final Map<String, Symbol> stringMap = new HashMap<String, Symbol>();
    
    public Symbol createHole(){
    	return HOLE;
    }

	public Symbol createMethod(String name, Symbol receiver, Symbol[] arguments) {
		return new Method(name, receiver, arguments);
	}

	public Symbol createField(String name, Symbol receiver) {
		return new Field(name, receiver);
	}

	public Symbol createConstructor(String type, Symbol receiver, Symbol[] arguments) {
		return new Method(type, receiver, arguments);
	}

	public Symbol createConstructor(String name) {
		return new Method(name);
	}

	public Symbol createField(String name) {
		return new Field(name);
	}

	public Symbol createMethod(String name) {
		return new Method(name);
	}

	public Symbol createVariable(String name) {
		return new Variable(name);
	}

	public Symbol createBooleanLiteral(boolean value) {
		if(value) return TRUE;
		else return FALSE;
	}

	public Symbol createCharacterLiteral(char value) {
		if(!charMap.containsKey(value)){
			charMap.put(value, new CharacterLiteral(value));
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
			stringMap.put(value, new StringLiteral(value));
		}
		return null;
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
}
