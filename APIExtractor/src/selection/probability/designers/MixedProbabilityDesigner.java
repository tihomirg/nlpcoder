package selection.probability.designers;

import selection.CentralizedShare;

public class MixedProbabilityDesigner extends ProbabilityDesigner {

	private UniformProbabilityDesigner uniform;
	private ConeProbabilityDesigner cone;
	
	public MixedProbabilityDesigner(double lowerLimit){
		this.uniform = new UniformProbabilityDesigner();
		this.cone = new ConeProbabilityDesigner(lowerLimit);
	}
	
	@Override
	public CentralizedShare getOneSideShare(int rightLength) {
		this.uniform.setFactor(this.factor);
		return this.uniform.getOneSideShare(rightLength);
	}

	@Override
	public CentralizedShare getDoubleSideCenterShare(int leftLength, int rightLength) {
		this.cone.setFactor(this.factor);
		return this.cone.getDoubleSideCenterShare(leftLength, rightLength);
	}
}
