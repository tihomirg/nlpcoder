import java.util.LinkedList;
import java.util.List;


public abstract class IntGenerator extends ParameterGenerator<Integer> {
	
	private int delta;
	private int min;
	private int max;
	
	public IntGenerator(String name, int initialVal, int delta) {
		this(name, initialVal, delta, 0);
	}	
	
	public IntGenerator(String name, int initialVal, int delta, int leftAndRight) {
		this(name, initialVal, delta, leftAndRight, leftAndRight);
	}
	
	public IntGenerator(String name, int initialVal, int delta, int left, int right) {
		super(name, calcValues(initialVal - delta * left, initialVal + delta * right, delta), left);
		this.delta = delta;
		this.min = left;
		this.max = right;
	}

	private static List<Integer> calcValues(int first, int last, int delta) {
		List<Integer> values = new LinkedList<Integer>();
		for (int i = first; i <= last ; i+=delta) {
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
