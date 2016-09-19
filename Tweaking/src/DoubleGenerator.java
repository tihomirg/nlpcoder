import java.util.LinkedList;
import java.util.List;


public abstract class DoubleGenerator extends ParameterGenerator<Double> {

	private double delta;
	private double min;
	private double max;

	public DoubleGenerator(String name, double initialVal, double delta, double min, double max) {
		this(name, initialVal, delta, 0);
		this.min = min;
		this.max = max;
	}
	
	public DoubleGenerator(String name, double initialVal, double delta) {
		this(name, initialVal, delta, 0);
	}
	
	public DoubleGenerator(String name, double initialVal, double delta, int leftAndRight) {
		this(name, initialVal, delta, leftAndRight, leftAndRight);
	}
	
	public DoubleGenerator(String name, double initialVal, double delta, int left, int right) {
		super(name, calcValues(initialVal - delta * left, initialVal + delta * right, delta), left);
		this.delta = delta;
	}

	private static List<Double> calcValues(double first, double last, double delta) {
		List<Double> values = new LinkedList<Double>();
		for (double i = first; i <= last ; i+=delta) {
			values.add(i);
		}
		return values;
	}
	
	@Override
	protected void incValue() {
		this.values.set(0, Math.min(this.max, this.values.get(0) + this.delta));
	}
	
	@Override
	protected void decValue() {
		this.values.set(0, Math.max(this.min, this.values.get(0) - this.delta));		
	}
}
