
public class Goal {

	private int lower;
	private int upper;

	public Goal(int upper) {
		this(0, upper);
	}
	
	public Goal(int lower, int upper) {
		this.lower = lower;
		this.upper = upper;
	}
	
	public int evaluate(int index) {
		if(lower <= index  && upper >= index) return 1;
		else return 0;
	}

}
