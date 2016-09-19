package tweak.intervals;

public class DoubleIntervalValue implements IntervalValue {

	private double value;
	
	public DoubleIntervalValue() {
	}
	
	public DoubleIntervalValue(double value) {
		this.value = value;
	}
	
	@Override
	public IntervalValue add(IntervalValue value) {
		return new DoubleIntervalValue(this.value+ value.asDoubleValue());
	}

	@Override
	public IntervalValue add(int value) {
		return new DoubleIntervalValue(this.value+ value);
	}

	@Override
	public IntervalValue sub(IntervalValue value) {
		return new DoubleIntervalValue(this.value - value.asDoubleValue());
	}

	@Override
	public IntervalValue sub(int value) {
		return new DoubleIntervalValue(this.value - value);
	}

	@Override
	public IntervalValue dev(int value) {
		return new DoubleIntervalValue(this.value /value);
	}

	@Override
	public int asIntValue() {
		return (int) this.value;
	}

	@Override
	public double asDoubleValue() {
		return this.value;
	}
}
