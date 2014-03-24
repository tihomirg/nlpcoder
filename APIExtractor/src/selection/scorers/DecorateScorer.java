package selection.scorers;

import selection.parser.one.Word;

public abstract class DecorateScorer extends Scorer {

	protected Scorer scorer;
	
	public DecorateScorer(Scorer score) {
		this.scorer = score;
	}

	@Override
	public double getScore(Word key) {
		return getScore(scorer.getScore(key));
	}
	
	public abstract double getScore(double score);

	@Override
	public void clear() {
		scorer.clear();
	}

	public String toString(int contextIndex){
		return scorer.toString(contextIndex);
	}
}
