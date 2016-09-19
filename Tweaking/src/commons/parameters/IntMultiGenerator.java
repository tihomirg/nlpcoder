package commons.parameters;
import java.util.LinkedList;
import java.util.List;

import tweak.intervals.Interval;
import tweak.intervals.IntervalValue;
import tweak.randomrefinement.RandomRefinementIntervalGenerator;


public abstract class IntMultiGenerator extends MultiParameterGenerator<Integer> {

	public IntMultiGenerator(String name, int initialVal, int delta, int leftAndRight) {
		this(name, initialVal, delta, leftAndRight, leftAndRight);
	}
	
	public IntMultiGenerator(String name, int initialVal, int delta, int left, int right) {
		super(name, calcValues(initialVal - delta * left, initialVal + delta * right, delta), left);
	}

	public IntMultiGenerator(RandomRefinementIntervalGenerator<IntervalValue> intervalGenerator) {
		this(intervalGenerator.getName(), intervalGenerator.getInterval());
	}

	public IntMultiGenerator(String name, Interval<IntervalValue> interval) {
		super(name, calcValues(interval), interval.getIntervalSize());
	}

	private static List<Integer> calcValues(Interval<IntervalValue> interval) {
		List<Integer> list = new LinkedList<Integer>();
		for (IntervalValue value: interval.getValues()) {
			list.add(value.asIntValue());
		}
		return list;
	}
	
	private static List<Integer> calcValues(int first, int last, int delta) {
		List<Integer> values = new LinkedList<Integer>();
		for (int i = first; i <= last ; i+=delta) {
			values.add(i);
		}
		return values;
	}
}
