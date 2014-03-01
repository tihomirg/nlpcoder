package selection.parser.three;

import java.util.Arrays;
import java.util.List;

import selection.IParser;
import selection.ISentence;
import selection.NormalProbabilityDesigner;
import selection.NormalShare;
import selection.parser.one.Word;
import selection.parser.two.ConstituentTwo;
import selection.parser.two.Level;
import selection.parser.two.SentenceTwo;
import selection.parser.two.Wordset;

public class ParserThree extends IParser {
	
	private NormalProbabilityDesigner nd;
	private int intervalDiameter;

	public ParserThree(int intervalDiameter) {
		this(new NormalProbabilityDesigner(), intervalDiameter);
	}

	public ParserThree(NormalProbabilityDesigner nd, int intervalDiameter) {
		this.nd = nd;
		this.intervalDiameter = intervalDiameter;
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
		nd.setFactor(1.0/constituentTwo.getImportantLength());
		
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
		
		nd.setFactor(wordset.getProbability());
		System.out.println("Factor"+nd.getFactor());
		
		NormalShare share = nd.getOneSideShare(right);
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
		int left = Math.max(0, i-intervalDiameter);
	    int right = Math.min(i+intervalDiameter, lenght-1);
	    
	    System.out.println("Left="+left+" Right="+right);
	    
	    NormalShare share = nd.getDoubleSideCenterShare(i-left, right-i);
	    double[] newProbs = share.toArray();
	    System.out.println(Arrays.toString(newProbs));
	    for (int j = left; j <= right; j++) {
			probs[j] += newProbs[j-left];
		}
	}
}
