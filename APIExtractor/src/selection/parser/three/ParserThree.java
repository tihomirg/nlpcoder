package selection.parser.three;

import java.util.Arrays;
import java.util.List;

import selection.IParser;
import selection.ISentence;
import selection.CentralizedShare;
import selection.parser.one.Word;
import selection.parser.two.ConstituentTwo;
import selection.parser.two.Level;
import selection.parser.two.SentenceTwo;
import selection.parser.two.Wordset;
import selection.probability.designers.ProbabilityDesigner;

public class ParserThree extends IParser {
	
	private ProbabilityDesigner pd;
	private int intervalRadius;

	public ParserThree(ProbabilityDesigner nd, int intervalRadius) {
		this.pd = nd;
		this.intervalRadius = intervalRadius;
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
		int first = constituentTwo.getFirstImporatantIndex();
		int last = constituentTwo.getLastImportantIndex();
		pd.setFactor(1.0/constituentTwo.getImportantLength());
		
		Wordset[] wordsets = constituentTwo.getWordsets();
		
		double[] probs = new double[wordsets.length];
		
		for (int i = first; i <= last; i++) {
			setInterval(i, probs);
		}
		
		for (int i = 0; i < probs.length; i++) {
			Wordset wordset = wordsets[i];
			wordset.setProbability(probs[i]);
			setWordset(wordset);
		}
		
	}

	private void setWordset(Wordset wordset) {
		Level[] levels = wordset.getLevels();
		int right = levels.length - 1;
		
		pd.setFactor(wordset.getProbability());
		
		CentralizedShare share = pd.getOneSideShare(right);
		double[] probs = share.toArray();
		
		for (int i = 0; i < probs.length; i++) {
			Level level = levels[i];
			level.setProbability(probs[i]);
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

	private void setInterval(int i, double[] probs) {
		int lenght = probs.length;
		int left = Math.max(0, i-intervalRadius);
	    int right = Math.min(i+intervalRadius, lenght-1);
	    
	    System.out.println("Left="+left+" Right="+right);
	    
	    CentralizedShare share = pd.getDoubleSideCenterShare(i-left, right-i);
	    double[] newProbs = share.toArray();
	    System.out.println(Arrays.toString(newProbs));
	    for (int j = left; j <= right; j++) {
			probs[j] += newProbs[j-left];
		}
	}
}
