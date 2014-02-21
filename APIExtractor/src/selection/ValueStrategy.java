package selection;

public class ValueStrategy {
	
	private double init = 10.0;
	
	public double getVal(int groupIndex) {
		return init / (groupIndex + 1);
	}

}
