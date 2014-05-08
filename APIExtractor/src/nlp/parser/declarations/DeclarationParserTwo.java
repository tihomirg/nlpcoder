package nlp.parser.declarations;

import java.util.List;

import nlp.parser.one.Word;
import nlp.parser.one.trees.SentenceOne;

import selection.CentralizedShare;

public class DeclarationParserTwo extends IDeclarationParser {

	private double lowLevel;
	private UniformProbabilityFactory designer;
	private int[] avgWordLength;
	private double nullProb;
	
	public DeclarationParserTwo(double lowLevel, int[] avgWordLength, double nullProb) {
		this.lowLevel = lowLevel;
		this.designer = new UniformProbabilityFactory();
		this.avgWordLength = avgWordLength;
		this.nullProb = nullProb;
	}

	public IDeclarationSentence parse(DeclarationSentenceOne curr) { 
		List<SentenceOne> result = curr.getResult();
		
		int size = result.size();
		double[] shares = getShares(size);
		
		for(int i=0; i< size; i++){
			SentenceOne sentenceOne = result.get(i);
			Word[] words = sentenceOne.getWords();
			int length = words.length;
			
			if (length == 0) continue;
			
			designer.setFactor(shares[i] * (1.0 - Math.max(0, avgWordLength[i] - length) * nullProb));
			assert length > 0;
			CentralizedShare oneSideShare = designer.getOneSideShare(length - 1);			
			
			double[] probs = oneSideShare.toArray();
			for(int j=0; j < length; j++){
				words[j].setProbability(probs[j]);
			}
		}
		return curr;
	}

	private double[] getShares(int size) {
		
		if (size == 1) return new double[]{1.0};
		
		double lowLevelDelta = lowLevel / size;
		double rest = 1.0 - lowLevel;
		
		double delta = 2*rest /((size-1) *size);
		double[] a = new double[size];
		
		for(int i=0; i < size; i++){
			a[i] = lowLevelDelta + ((size-(i+1))*delta);
		}
		
		return a;
	}
}
