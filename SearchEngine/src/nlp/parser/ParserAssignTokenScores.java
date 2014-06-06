package nlp.parser;

import java.util.List;

import search.WToken;

public class ParserAssignTokenScores implements IParser {

	private double[] scores;
	private int[] indexes;

	public ParserAssignTokenScores(double[] scores, int[] indexes) {
		this.scores = scores;
		this.indexes = indexes;
	}
	
	@Override
	public Input parse(Input input) {
		for (Sentence sentence : input.getSentences()) {
			for (Group group: sentence.getSearchKeyAndLiteralGroups()) {
				List<List<WToken>> levels = group.getLevels();
				for (int i = 0; i < levels.size(); i++) {
					assign(levels.get(i), scores[i], indexes[i]);
				}
			}
		}
		
		return input;
	}

	private void assign(List<WToken> list, double score, int index) {
		for (WToken wToken : list) {
			wToken.setScore(score);
			wToken.setIndex(index);
		}
	}
}
