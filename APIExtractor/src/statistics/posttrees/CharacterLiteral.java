package statistics.posttrees;

import java.util.List;

public class CharacterLiteral extends Expr {

	private String value;

	@Override
	public void addArgs(List<Expr> args) {
		this.value = args.get(0).getString();
	}

}