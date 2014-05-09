package statistics.posttrees;

import java.util.List;

public class BooleanLiteral extends Expr {

	private boolean value;
	
	@Override
	public void addArgs(List<Expr> args) {
		String string = args.get(0).getString();
		value = Boolean.parseBoolean(string);
	}

}
