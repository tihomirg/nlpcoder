package selection.scorers.config;

import java.util.HashMap;
import java.util.Map;

public class Config {
	private static final Map<Integer, Double> scores = new HashMap<Integer, Double>(){ {put(0, 5.0); put(1, 1.0);}};

	public static Map<Integer, Double> getScores() {
		return scores;
	}
	
}
