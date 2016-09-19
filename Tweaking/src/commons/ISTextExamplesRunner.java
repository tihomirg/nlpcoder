package commons;

import java.util.List;

import commons.examples.Example;
import commons.scores.Score;
import commons.values.Ordered;
import api.Local;
import search.ISText;

public abstract class ISTextExamplesRunner<T, V extends Ordered<V>> {

	protected static final Local[] EMPTY_LOCALS = new Local[0];
	protected List<Example<T, V>> examples;
	protected Evaluator<T, V> eval;
	
	public ISTextExamplesRunner(ISText iSText, Class<?> clazz) {
		this.examples = getExamples(iSText);
		this.eval = new Evaluator<T, V>(iSText, clazz, examples);
	}
	
	protected abstract List<Example<T, V>> getExamples(ISText iSText);
	
	public Score<V> run() {
		eval.run();
		return eval.getTotalScore();
	}
}
