package selection.scorers;

import java.util.Arrays;

import selection.parser.one.Word;

public class AddScorer extends Scorer {

	private Scorer[] scorers;
	
	public AddScorer(Scorer[] scorers) {
		this.scorers = scorers;
	}

	@Override
	public double getScore(Word key) {
		double val = 0;
		for (Scorer scorer : scorers) {
			val += scorer.getScore(key);
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
		return "AddScorer [" + Arrays.toString(scorers) + "]";
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
