package commons.parameters;

import java.util.List;

public interface ParameterGenerator<T extends Number> {
	public boolean hasNext();
	public boolean hasPrev();
	public void gotoNext();
	public void gotoPrev();
	public void reset();
	public void set();
	public void setIndex(int index);
	public int getIndex();
	public int getSize();
	public T getVal();
	public List<T> getValues();
	public String getName();
}
