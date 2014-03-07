package selection.probability.designers;

import selection.CentralizedShare;

public abstract class ProbabilityDesigner {

	protected double factor = 1;
	
	public double getFactor() {
		return factor;
	}

	public void setFactor(double factor) {
		this.factor = factor;
	}

	public abstract CentralizedShare getOneSideShare(int rightLength);

	public abstract CentralizedShare getDoubleSideCenterShare(int leftLength, int rightLength);

}