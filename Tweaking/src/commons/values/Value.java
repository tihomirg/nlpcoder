package commons.values;


public class Value implements Ordered<Value> {

	private int value;

	public Value(int value) {
		this.value = value;
	}

	public Value() {
	}

	public void inc(){
		this.value++;
	}
	
	public void add(Value that){
		this.value += that.value;
	}
	
	@Override
	public boolean isBetterThan(Value that) {
		return this.value > that.value;
	}

	@Override
	public String toString() {
		return "Value [value=" + value + "]";
	}
}
