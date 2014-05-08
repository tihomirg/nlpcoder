package nlp.parser.declarations;

import selection.CentralizedShare;

public class UniformProbabilityFactory {

	private double factor = 1;

	public CentralizedShare getOneSideShare(int rightLength) {
		double prob = ((double) factor) / (rightLength + 1);
		return new CentralizedShare(setArrayElements(rightLength, prob), prob);
	}

	public CentralizedShare getDoubleSideCenterShare(int leftLength, int rightLength) {
		double prob = ((double) factor) / (leftLength + rightLength + 1);
		return new CentralizedShare(setArrayElements(leftLength, prob), setArrayElements(rightLength, prob), prob);
	}
	
	private double[] setArrayElements(int length, double prob) {
		double[] a = new double[length];
		for (int i = 0; i < a.length; i++) {
			a[i] = prob;
		}
		return a;
	}

	public double getFactor() {
		return factor;
	}

	public void setFactor(double factor) {
		this.factor = factor;
	}	

}
