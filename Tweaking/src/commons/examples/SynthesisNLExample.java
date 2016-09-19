package commons.examples;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import api.Local;
import search.ISText;
import commons.goals.Goal;
import commons.scores.Score;
import commons.values.RankedValue;
import commons.values.Value;

public class SynthesisNLExample implements Example<String, RankedValue> {

	protected String input;
	protected List<Local> locals;
	private Goal<String, RankedValue> goal;
	private Score<RankedValue> score;
	
	public SynthesisNLExample(String input, Goal<String, RankedValue> goal) {
		this(input, new LinkedList<Local>(), goal);
	}
	
	public SynthesisNLExample(String input, List<Local> locals, Goal<String, RankedValue> goal) {
		this.input = input;
		this.locals = locals;
		this.goal = goal;
	}

	@Override
	public List<String> run(ISText iSText) {
		List<String> result = Arrays.asList(iSText.run(input, locals));
		this.score = goal.getScore(result);
		return result;
	}

	@Override
	public Score<RankedValue> getScore() {
		return this.score;
	}

	@Override
	public int size() {
		return this.goal.size();
	}

	@Override
	public String getExprectedOuptup() {
		return this.goal.getExpectedOutput();
	}

	@Override
	public String getInput() {
		return this.input;
	}
}
