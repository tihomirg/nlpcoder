package commons.parameters;

import java.util.List;

public abstract class MultiParameterGenerator<T extends Number> implements ParameterGenerator<T>{

	private String name;
	protected List<T> values;

	protected int index;
	protected int size;
	
	public MultiParameterGenerator(String name, List<T> values, int startIndex) {
		this.name = name;
		this.values = values;
		this.size = values.size();
		this.index = startIndex;
	}

	public T getVal(){
		return values.get(index);
	}
	
	public List<T> getValues() {
		return values;
	}

	public void gotoNext() {
		index++;
	}
	
	public void gotoPrev(){
		index--;
	}

	public void reset(){
		this.index = 0;
	}

	public boolean hasNext() {
		return index + 1 < size;
	}

	public boolean hasPrev() {
		return index - 1 >= 0;
	}	
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getSize() {
		return this.values.size();
	}

	@Override
	public String toString() {
		return "ParameterGenerator [name=" + name + ", values=" + values+ ", index=" + index + "]";
	}

	@Override
	public int getIndex() {
		return this.index;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
}
