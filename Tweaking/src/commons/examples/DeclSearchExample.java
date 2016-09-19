package commons.examples;

import java.util.LinkedList;
import java.util.List;

import commons.goals.Goal;
import commons.scores.Score;
import commons.values.RankedValue;
import api.Local;
import search.ISText;
import search.SearchReport;

public class DeclSearchExample implements Example<SearchReport, RankedValue> {

	protected String input;
	protected List<Local> locals;
	private Goal<SearchReport, RankedValue> goal;
	private Score<RankedValue> score;
	
	public DeclSearchExample(String input, Goal<SearchReport, RankedValue> goal) {
		this(input, new LinkedList<Local>(), goal);
	}
	
	public DeclSearchExample(String input, List<Local> locals, Goal<SearchReport, RankedValue> goal) {
		this.input = input;
		this.locals = locals;
		this.goal = goal;
	}

	@Override
	public List<SearchReport> run(ISText iSText) {
		List<SearchReport> result = iSText.runForDeclarationSearch(input, locals);
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
