package core;

import java.util.List;

import search.WToken;
import nlp.parser.Group;
import nlp.parser.IParser;
import nlp.parser.Input;
import nlp.parser.Sentence;

public class ParserAssignTokenScores implements IParser {

	private double[] scores;

	public ParserAssignTokenScores(double[] scores) {
		this.scores = scores;
	}
	
	@Override
	public Input parse(Input input) {
		for (Sentence sentence : input.getSentences()) {
			for (Group group: sentence.getSearchKeyGroups()) {
				List<List<WToken>> levels = group.getLevels();
				for (int i = 0; i < levels.size(); i++) {
					assign(levels.get(i), scores[i]);
				}
			}
		}
		
		return input;
	}

	private void assign(List<WToken> list, double score) {
		for (WToken wToken : list) {
			wToken.setScore(score);
		}
		
	}

}
