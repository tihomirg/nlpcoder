package commons.parameters;
import java.util.LinkedList;
import java.util.List;

import tweak.intervals.DoubleIntervalValue;
import tweak.intervals.Interval;
import tweak.intervals.IntervalValue;
import tweak.randomrefinement.RandomRefinementIntervalGenerator;


public abstract class DoubleMultiGenerator extends MultiParameterGenerator<Double> {
	
	public DoubleMultiGenerator(String name, double initialVal, double delta, int leftAndRight) {
		this(name, initialVal, delta, leftAndRight, leftAndRight);
	}
	
	public DoubleMultiGenerator(String name, double initialVal, double delta, int left, int right) {
		super(name, calcValues(initialVal - delta * left, initialVal + delta * right, delta), left);
	}

	public DoubleMultiGenerator(RandomRefinementIntervalGenerator<IntervalValue> intervalGenerator) {
		this(intervalGenerator.getName(), intervalGenerator.getInterval());
	}

	public DoubleMultiGenerator(String name, Interval<IntervalValue> interval) {
		super(name, calcValues(interval), interval.getIntervalSize());
	}

	private static List<Double> calcValues(Interval<IntervalValue> interval) {
		List<Double> list = new LinkedList<Double>();
		for (IntervalValue value: interval.getValues()) {
			list.add(value.asDoubleValue());
		}
		return list;
	}

	private static List<Double> calcValues(double first, double last, double delta) {
		List<Double> values = new LinkedList<Double>();
		for (double i = first; i <= last ; i+=delta) {
			values.add(i);
		}
		return values;
	}
}
