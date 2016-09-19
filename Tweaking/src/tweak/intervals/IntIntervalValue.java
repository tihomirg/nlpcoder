package tweak.intervals;

public class IntIntervalValue implements IntervalValue {

	private int value;
	
	public IntIntervalValue() {
	}
	
	public IntIntervalValue(int value) {
		this.value = value;
	}
	
	@Override
	public IntervalValue add(IntervalValue value) {
		return new IntIntervalValue(this.value + value.asIntValue());
	}

	@Override
	public IntervalValue add(int value) {
		return new IntIntervalValue(this.value + value);
	}

	@Override
	public IntervalValue sub(IntervalValue value) {
		return new IntIntervalValue(this.value - value.asIntValue());
	}

	@Override
	public IntervalValue sub(int value) {
		return new IntIntervalValue(this.value - value);
	}

	@Override
	public IntervalValue dev(int value) {
		return new IntIntervalValue(this.value / value);
	}

	@Override
	public int asIntValue() {
		return this.value;
	}

	@Override
	public double asDoubleValue() {
		return this.value;
	}

}
