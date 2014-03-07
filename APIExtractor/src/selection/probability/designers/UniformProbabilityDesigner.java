package selection.probability.designers;

import selection.CentralizedShare;

public class UniformProbabilityDesigner extends ProbabilityDesigner {

	@Override
	public CentralizedShare getOneSideShare(int rightLength) {
		double prob = ((double) factor) / (rightLength + 1);
		return new CentralizedShare(setArrayElements(rightLength, prob), prob);
	}

	@Override
	public CentralizedShare getDoubleSideCenterShare(int leftLength, int rightLength) {
		double prob = ((double) factor) / (leftLength+ rightLength + 1);
		return new CentralizedShare(setArrayElements(leftLength, prob), setArrayElements(rightLength, prob), prob);
	}
	
	private double[] setArrayElements(int length, double prob) {
		double[] a = new double[length];
		for (int i = 0; i < a.length; i++) {
			a[i] = prob;
		}
		return a;
	}	

}
