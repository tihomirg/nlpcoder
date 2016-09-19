package tweak.intervals;

public interface IntervalValue {
	public IntervalValue add(IntervalValue value);
	public IntervalValue add(int value);
	public IntervalValue sub(IntervalValue value);
	public IntervalValue sub(int value); 
	public IntervalValue dev(int value);
	public int asIntValue();
	public double asDoubleValue();
}
