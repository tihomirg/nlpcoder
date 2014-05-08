package selection.scorers;

import nlp.parser.one.Word;

public class DeclFreqModelScorer extends Scorer {

	private double score;

	public DeclFreqModelScorer(double score) {
		this.score = score;
	}

	@Override
	public double getScore(Word key) {
		return this.score;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
	}

	@Override
	public String toString(int contextIndex) {
		return Double.toString(score);
	}

}
