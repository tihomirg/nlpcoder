package statistics.posttrees;

import java.util.List;
import java.util.Map;

public abstract class Expr {

	private int frequency;

	public abstract void addArgs(List<Expr> args);
	
	public String getString() {
		throw new UnsupportedOperationException();
	}

	public void setFrequency(int value) {
		this.frequency = value;
	}

}
