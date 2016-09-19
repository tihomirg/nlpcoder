package commons.goals;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import search.SearchReport;
import commons.scores.SingleScore;
import commons.values.Value;

public class DummyGoal implements Goal<SearchReport, Value> {

	@Override
	public SingleScore<Value> getScore(List<SearchReport> actualOutput) {
		return new SingleScore<Value>(new Value());
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public String getExpectedOutput() {
		throw new UnsupportedOperationException();
	}

}
