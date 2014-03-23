package selection.scorers;

import java.util.Arrays;

import selection.parser.one.Word;

public class MultiScorer extends Scorer{
	private Scorer[] scorers;
	
	public MultiScorer(Scorer[] scorers) {
		this.scorers = scorers;
	}

	@Override
	public double getScore(Word key) {
		double val = 1;
		for (Scorer scorer : scorers) {
			val *= scorer.getScore(key);
		}
		return val;
	}

	@Override
	public void clear() {
		for (Scorer scorer : scorers) {
			scorer.clear();
		}
	}

	@Override
	public String toString() {
		return "MultiScorer [" + Arrays.toString(scorers) + "]";
	}
}