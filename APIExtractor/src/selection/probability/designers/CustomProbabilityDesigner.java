package selection.probability.designers;

import selection.CentralizedShare;

public class CustomProbabilityDesigner extends ProbabilityDesigner {

	private double scores[];
	
	public CustomProbabilityDesigner(double[] scores) {
		this.scores = scores;
	}

	@Override
	public CentralizedShare getOneSideShare(int rightLength) {
		double[] right = new double[rightLength];
		System.arraycopy(scores, 1, right, 0, rightLength);
		
		return new CentralizedShare(right, scores[0]);
	}

	@Override
	public CentralizedShare getDoubleSideCenterShare(int leftLength, int rightLength) {
		double[] right = new double[rightLength];
		System.arraycopy(scores, 1, right, 0, rightLength);		
		double[] left = new double[leftLength];
		System.arraycopy(scores, 1, right, 0, leftLength);		
		return new CentralizedShare(left, right, scores[0]);
	}
}
