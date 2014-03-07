package selection.probability.designers;

import java.util.Arrays;

import selection.CentralizedShare;

public class ConeProbabilityDesigner extends ProbabilityDesigner {

	private UniformProbabilityDesigner uniform = new UniformProbabilityDesigner();
	private double lowerLimit;
	
	public ConeProbabilityDesigner(double lowerLimit){
		this.lowerLimit = lowerLimit;
	}
	
	@Override
	public CentralizedShare getOneSideShare(int rightLength) {
		if (rightLength == 0) return new CentralizedShare(this.factor);
				
		double intervals = rightLength+1;
		double share = 1.0 - lowerLimit;
		double deltaLowerLimit = lowerLimit / intervals;
		double delta = 2*share / (rightLength+1 * rightLength);
		
		double[] a = new double[rightLength];		
		
		double center = this.factor *(deltaLowerLimit + delta * rightLength);
		
		for(int i=0; i < rightLength; i++){
			a[i] = this.factor *(deltaLowerLimit + (rightLength - (i+1))*delta);
		}
		
		return new CentralizedShare(a, center);		
	}

	@Override
	public CentralizedShare getDoubleSideCenterShare(int leftLength, int rightLength) {
		int max = Math.max(leftLength, rightLength);
		
		if (max == 0) return new CentralizedShare(this.factor);
		
		double intervals = 2* max+1;
		double share = 1.0 - lowerLimit;
		double deltaLowerLimit = lowerLimit / intervals;
		double delta = share / (max * max);
		
		double[] a = new double[max];
		
		int diff = Math.abs(leftLength - rightLength);
		double factor = this.factor / (1.0 - diff *(deltaLowerLimit + (diff-1) * delta / 2));

		double center = factor* (deltaLowerLimit + delta * max);
		for (int i = 0; i < a.length; i++) {
			a[i] = factor * (deltaLowerLimit + (max - (i+1))*delta);
		}
		
		double[] rightArray = Arrays.copyOf(a, rightLength);
		double[] leftArray = Arrays.copyOf(a, leftLength);
		
		for (int i = 0; i < leftArray.length / 2; i++) {
			double temp = leftArray[i];
			leftArray[i] = leftArray[leftArray.length-1-i];
			leftArray[leftArray.length-1-i] = temp;
		}
		
		return new CentralizedShare(leftArray, rightArray, center);
	}

}
