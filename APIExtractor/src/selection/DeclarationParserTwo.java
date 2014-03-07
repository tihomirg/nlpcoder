package selection;

import java.util.List;

import selection.parser.one.Word;
import selection.parser.one.trees.SentenceOne;
import selection.probability.designers.UniformProbabilityDesigner;

public class DeclarationParserTwo extends IDeclarationParser {

	private double lowLevel;
	private UniformProbabilityDesigner designer;
	
	public DeclarationParserTwo(double lowLevel) {
		this.lowLevel = lowLevel;
		this.designer = new UniformProbabilityDesigner();
	}

	public IDeclarationSentence parse(DeclarationSentenceOne curr) { 
		List<SentenceOne> result = curr.getResult();
		
		int size = result.size();
		double[] shares = getShares(size);
		
		for(int i=0; i< size; i++){
			SentenceOne sentenceOne = result.get(i);
			Word[] words = sentenceOne.getWords();
			designer.setFactor(shares[i]);
			int length = words.length;
			assert length > 0;
			CentralizedShare oneSideShare = designer.getOneSideShare(length -1);
			double[] probs = oneSideShare.toArray();
			for(int j=0; j < length; j++){
				words[i].setProbability(probs[i]);
			}
		}
		return curr;
	}

	private double[] getShares(int size) {
		
		if (size == 1) return new double[]{1.0};
		
		double lowLevelDelta = lowLevel / size;
		double rest = 1.0 - lowLevelDelta;
		
		double delta = 2*rest /((size-1) *size);
		double[] a = new double[size];
		
		for(int i=0; i < size; i++){
			a[i] = lowLevelDelta + ((size-(i+1))*delta);
		}
		
		return a;
	}
}
