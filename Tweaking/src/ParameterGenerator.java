import java.util.List;

import commons.scores.SingleScore;
import commons.values.Value;

public abstract class ParameterGenerator<T extends Number> {

	private String name;

	protected List<T> values;

	protected int index;
	protected int size;

	protected SingleScore<Value> bestScore;
	protected int bestIndex;

	public ParameterGenerator(String name, List<T> values, int bestIndex) {
		this.name = name;
		this.values = values;
		this.size = values.size();
		this.bestIndex = bestIndex;
		setBest();
	}

	public T getVal(){
		return values.get(index);
	}
	
	public List<T> getValues() {
		return values;
	}

	public void next() {
		index++;
	}

	public void reset(){
		index = 0;
	}

	public abstract void set();

	public boolean hasMore() {
		return index + 1 < size;
	}

	public void setBest() {
		index = bestIndex;
	}
	
	public void clear() {
		this.index = 0;
		this.bestScore = null;
	}
	
	public void setBest(int bestIndex) {
		this.bestIndex = bestIndex;
		this.setBest();
	}

	public void updateScore(SingleScore score) {
		if (bestScore == null) {
			bestScore = score;
		} else {
			if (score.isBetterThan(bestScore)) {
				this.bestScore = score;
				this.bestIndex = index;
			}
		}
	}

	public int getSize() {
		return this.values.size();
	}

	@Override
	public String toString() {
		return "ParameterGenerator [name=" + name + ", values=" + values
				+ ", index=" + index + ", size=" + size + ", bestScore="
				+ bestScore + ", bestIndex=" + bestIndex + "]";
	}
	
	public String toBriefString(){
		return this.name+" = "+values.get(bestIndex);
	}

	public void setUniqueValue(double rand){
		if(rand > 0.67) this.incValue();
		else if (rand < 0.33) this.decValue();
	}

	protected abstract void decValue();

	protected abstract void incValue();
}
