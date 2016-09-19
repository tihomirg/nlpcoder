package commons.values;


public class RankedValue implements Ordered<RankedValue>{

	private int value;
	private int rank;
	
	public RankedValue() {}
	public RankedValue(int rank) {
		this.value = rank >= 0 ? 1: 0;
		this.rank = rank;
	}
	@Override
	
	public boolean isBetterThan(RankedValue that) {
		if (this.value == that.value){
			return this.rank < that.rank;
		} else {
			return this.value > that.value;
		}
	}
	@Override
	public void inc() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void add(RankedValue that) {
		this.value+= that.value;
		this.rank+= that.rank >= 0 ? that.rank : 0;
	}
	
	public static ValueFactory<RankedValue> getFactory(){
		return new ValueFactory<RankedValue>();
	}
	
	@Override
	public String toString() {
		return "RankedValue [value=" + value + ", rank=" + rank + "]";
	}
	
	public int getRank() {
		return rank;
	}
}
