import java.util.LinkedList;
import java.util.List;

import commons.scores.SingleScore;
import commons.values.Value;


public class Result {

	private SingleScore<Value> score;
	private List<String> settings;

	public Result(SingleScore<Value> score, List<ParameterGenerator> generators) {
		this.score = score;
		this.settings = getSettings(generators);
	}

	private List<String> getSettings(List<ParameterGenerator> generators) {
		List<String> settings = new LinkedList<String>();
		
		for (ParameterGenerator generator: generators) {
			settings.add(generator.toBriefString());
		}
		
		return settings;
	}
	
	public SingleScore<Value> getScore() {
		return score;
	}
	
	@Override
	public String toString() {
		return "RandomWrapper [bestScore=" + score+"\n" 
	            + generatorsToBriefString()+ "]";
	}

	private String generatorsToBriefString() {
		StringBuffer sb = new StringBuffer();
		
		for (String settings: this.settings) {
			sb.append(settings+"\n");
		}
		
		return sb.toString();
	}

}
