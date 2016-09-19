package commons.strategy;

import commons.parameters.ParameterGenerator;
import commons.values.Ordered;

public class RoundRobinStrategy<T, V extends Ordered<V>> extends ParameterStrategy<T, V> {

	private int index;
	private ParameterGenerator<?> current;
	
	@Override
	public void next() {
		if(this.current == null){
			index = 0;
			this.current = paramGens.get(index);
			this.current.reset();
		} else if (!this.current.hasNext()){
			setCurrentToBestIndex();
			index = (index + 1) % paramGens.size();
			this.current = paramGens.get(index);
			this.current.reset();
		} else {
			this.current.gotoNext();
		}
	}

	private void setCurrentToBestIndex() {
		this.current.setIndex(this.bestReport.getIndex(this.current));
	}
}
