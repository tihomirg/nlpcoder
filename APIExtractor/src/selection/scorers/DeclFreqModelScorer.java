package selection.scorers;

import selection.DeclFreqMap;
import selection.parser.one.Word;

public class DeclFreqModelScorer extends Scorer {

	private DeclFreqMap fMap;
	private int id;

	public DeclFreqModelScorer(DeclFreqMap fMap, int id) {
		this.fMap = fMap;
		this.id = id;
	}

	@Override
	public double getScore(Word key) {
		return fMap.getProbability(id);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
	}

	@Override
	public String toString(int contextIndex) {
		return Double.toString(fMap.getProbability(id));
	}

}
