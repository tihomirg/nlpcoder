package commons.strategy;

import java.util.LinkedList;
import java.util.List;

import commons.parameters.ParameterGenerator;
import commons.values.Ordered;

public class RandomStrategy<T, V extends Ordered<V>> extends ParameterStrategy<T, V> {

	private ParameterGenerator<?> current;
	
	private List<ParameterGenerator<?>> notChosen;
	
	public RandomStrategy() {
		this.notChosen = new LinkedList<ParameterGenerator<?>>();
	}
	
	@Override
	public void next() {
		if(this.current == null){
			this.current = chooseRandom();
		} else if (!this.current.hasNext()){
			setCurrentToBestIndex();
			this.current = chooseRandom();
		} else {
			this.current.gotoNext();
		}
	}

	private ParameterGenerator<?> chooseRandom() {
		if (this.notChosen.isEmpty()) {
			this.notChosen.addAll(paramGens);
		}
		int index = (int) Math.floor(Math.random() * this.notChosen.size());
		ParameterGenerator<?> chosen = this.notChosen.get(index);
		this.notChosen.remove(chosen);
		chosen.reset();
		return chosen;
	}

	private void setCurrentToBestIndex() {
		this.current.setIndex(this.bestReport.getIndex(this.current));
	}
}
