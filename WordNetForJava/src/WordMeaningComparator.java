import java.util.Comparator;

import wordnet2.WordMeaning;


public class WordMeaningComparator implements Comparator<WordMeaning> {

	@Override
	public int compare(WordMeaning wm1, WordMeaning wm2) {
		double score1 = wm1.getScore();
		double score2 = wm2.getScore();
		
		if (score1 > score2) return -1;
		else if (score1 < score2) return 1;
		else return 0;
	}

}
