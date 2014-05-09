package statistics.posttrees;

import java.util.List;

public class StringExpr extends Expr {

	private String string;
	
	public StringExpr(String string) {
		this.string = string;
	}
	
	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	@Override
	public void addArgs(List<Expr> args) {

	}

}
