package statistics.pretrees;

import static statistics.parsers.Parser.*;
import java.util.List;
import statistics.Names;
import statistics.parsers.Parser;
import statistics.parsers.SingleResult;
import types.PrimitiveType;
import types.StabileTypeFactory;
import util.Pair;

public class CharacterLiteral extends Expr {

	private char value;

	public CharacterLiteral(char value, PrimitiveType type) {
		super(type);
		this.value = value;
	}

	@Override
	public String toString() {
		return Character.toString(value);
	}

	@Override
	public String shortRep() {
		return Names.CharacterLiteral;
	}

	@Override
	protected String argReps() {
		return Character.toString(value);
	}
	
	@Override
	protected void longReps(List<Pair<String, String>> list) {
	}

	public static SingleResult parseShort(String string, StabileTypeFactory tf) {
		return new SingleResult(createCharacterLiteral(), removeCharacterLiteral(string));
	}	

	@Override
	public boolean isLiteral() {
		return true;
	}
	
}
