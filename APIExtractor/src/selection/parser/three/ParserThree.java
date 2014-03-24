package selection.parser.three;

import java.util.List;

import selection.IParser;
import selection.ISentence;
import selection.parser.one.Word;
import selection.parser.two.ConstituentTwo;
import selection.parser.two.Level;
import selection.parser.two.SentenceTwo;
import selection.parser.two.Wordset;
import selection.probability.designers.ScoreDesigner;

public class ParserThree extends IParser {
	
	private ScoreDesigner pd;

	public ParserThree(ScoreDesigner sd) {
		this.pd = sd;
	}

	public ISentence parse(SentenceTwo curr) {
		setConstituents(curr.getConstituents());
		return curr;
	}

	private void setConstituents(ConstituentTwo[] constituents) {
		for (ConstituentTwo constituentTwo : constituents) {
			setConstituent(constituentTwo);
		}
	}

	private void setConstituent(ConstituentTwo constituentTwo) {
		Wordset[] wordsets = constituentTwo.getWordsets();
		double[] scores = pd.getScores(constituentTwo.getFirstImporatantIndex(), constituentTwo.getLastImportantIndex(), wordsets.length);
		
		for (int i = 0; i < scores.length; i++) {
			Wordset wordset = wordsets[i];
			wordset.setProbability(scores[i]);
			setWordset(wordset);
		}
	}

	private void setWordset(Wordset wordset) {
		double score = wordset.getProbability();
		Level[] levels = wordset.getLevels();
		for (Level level : levels) {
			level.setProbability(score);
			setLevel(level);
		}
	}

	private void setLevel(Level level) {
		List<Word> words = level.getWords();
		double probability = level.getProbability();
		for (Word word : words) {
			word.setProbability(probability);
		}
	}
}
