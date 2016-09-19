package commons.strategy;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import commons.parameters.ParameterGenerator;
import commons.scores.Score;
import commons.values.Ordered;

public abstract class ParameterStrategy<T, V extends Ordered<V>> {

	protected List<ParameterGenerator<?>> paramGens;
	protected FinalReport<T, V> bestReport;
	
	public void setParameterGenerators(List<ParameterGenerator<?>> paramGens) {
		this.paramGens = paramGens;
	}

	public abstract void next();

	public void updateScore(Score<V> newScore, List<List<T>> result) {
		if(bestReport == null || newScore.isBetterThan(bestReport.getScore())){
			bestReport = new FinalReport<T, V>(newScore, putIndexesAndGeneratorsIntoMap(), result);
		}
	}

	private Map<ParameterGenerator<?>, Integer> putIndexesAndGeneratorsIntoMap() {
		Map<ParameterGenerator<?>, Integer> map = new HashMap<ParameterGenerator<?>, Integer>();
		for (ParameterGenerator<?> param: this.paramGens) {
			map.put(param, param.getIndex());
		}
		return map;
	}

	public void set() {
		for (ParameterGenerator<?> param: paramGens) {
			param.set();
		}
	}
	
	@Override
	public String toString() {
		return this.bestReport.toString();
	}

}
