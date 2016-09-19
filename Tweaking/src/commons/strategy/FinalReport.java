package commons.strategy;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import commons.parameters.ParameterGenerator;
import commons.scores.Score;
import commons.values.Ordered;

public class FinalReport<T, V extends Ordered<V>> {
	private Score<V> score;
	private Map<ParameterGenerator<?>, Integer> parameters;
	private List<List<T>> output;
	
	public FinalReport(Score<V> score, Map<ParameterGenerator<?>, Integer> parameters, List<List<T>> output) {
		this.score = score;
		this.parameters = parameters;
		this.output = output;
	}

	public Score<V> getScore() {
		return score;
	}

	public void setScore(Score<V> score) {
		this.score = score;
	}

	public Integer getIndex(ParameterGenerator<?> genParam) {
		return parameters.get(genParam);
	}
	
	public List<List<T>> getOutput() {
		return output;
	}

	public void setOutput(List<List<T>> output) {
		this.output = output;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.score+"\n");
		//sb.append(this.best.getOutput()+"\n");
		for (Entry<ParameterGenerator<?>, Integer> param : parameters.entrySet()) {
			ParameterGenerator<?> parameterGenerator = param.getKey();
			Integer index = param.getValue();
			sb.append(parameterGenerator.getName()+ " : "+parameterGenerator.getValues().get(index)+"\n");
		}
		return sb.toString();
	}
}
