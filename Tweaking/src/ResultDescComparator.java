import java.util.Comparator;

import commons.scores.SingleScore;
import commons.values.Value;



public class ResultDescComparator implements Comparator<Result>{
	@Override
	public int compare(Result rd1, Result rd2) {
		SingleScore<Value> score1 = rd1.getScore();
		SingleScore<Value> score2 = rd2.getScore();
		
		if (score1.isBetterThan(score2)) return -1;
		else if (score2.isBetterThan(score1)) return 1;
		else return 0;
	}
}
