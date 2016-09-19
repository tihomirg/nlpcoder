package commons;

import java.util.List;

import commons.examples.Example;
import commons.parameters.ParameterGenerator;
import commons.strategy.ParameterStrategy;
import search.ISText;
import commons.scores.Score;
import commons.values.Ordered;
import api.Local;

public abstract class ISTextParameterGeneratorAndExampleRunner<T, V extends Ordered<V>> {
	
	protected static final Local[] EMPTY_LOCALS = new Local[0];
	protected ParameterStrategy<T, V> paramStrategy;
	private ISText iSText;
	private List<Example<T, V>> examples;
	private List<ParameterGenerator<?>> parameterGens;
	private Evaluator<T, V> eval;
	
	public ISTextParameterGeneratorAndExampleRunner(ISText iSText, Class<?> clazz, ParameterStrategy<T, V> paramStrategy) {
		this.iSText = iSText;
		this.examples = getExamples(iSText);
		this.parameterGens = getParameterGenerators();
		this.paramStrategy = paramStrategy;
		this.paramStrategy.setParameterGenerators(parameterGens);
		this.eval = new Evaluator<T, V>(iSText, clazz, examples);
	}
	
	protected abstract List<Example<T, V>> getExamples(ISText iSText);
	
	protected abstract List<ParameterGenerator<?>> getParameterGenerators();
	
	protected Score<V> run() {
		paramStrategy.set();
		List<List<T>> result = eval.run();
		Score<V> score = eval.getTotalScore();
		paramStrategy.updateScore(score, result);
		paramStrategy.next();
		return score;
	}
	
	//Num of (sub)goals we have to fulfill
	public int totalExampleGoalSize(){
		int size = 0;
		for (Example<T, V> example : this.examples) {
			size += example.size();
		}
		return size;
	}

	public abstract void tweak();
}