package commons.goals;

import java.util.List;

import commons.scores.RankedScore;
import commons.scores.Score;
import commons.scores.SingleScore;
import commons.values.RankedValue;
import commons.values.Value;

public class TopSnippetGoal implements Goal<String, RankedValue> {

	private String expectedOuput;
	private String name;

	public TopSnippetGoal(String name, String expectedOuput) {
		this.name = name;
		this.expectedOuput = expectedOuput;
	}
	
	@Override
	public Score<RankedValue> getScore(List<String> actualOutput) {
		for (int i = 0; i < actualOutput.size(); i++) {
			String output = actualOutput.get(i);
			if (output.equals(expectedOuput)) return new RankedScore(name, new RankedValue(i));
		}
		
		return new SingleScore<RankedValue>(name, new RankedValue(-1));
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public String getExpectedOutput() {
		return this.expectedOuput;
	}
	
}
