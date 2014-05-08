package selection.scorers;

import java.util.Arrays;

import nlp.parser.one.Word;


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

	@Override
	public String toString(int contextIndex) {
		StringBuffer sb = new StringBuffer("[");
		for (Scorer scorer : scorers) {
			sb.append(scorer.toString(contextIndex)+", ");
		}
		sb.append("]");
		return sb.toString();
	}
}