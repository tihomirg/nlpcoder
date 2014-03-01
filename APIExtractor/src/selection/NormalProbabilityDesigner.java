package selection;

import java.util.Arrays;

import org.apache.commons.math3.distribution.NormalDistribution;

public class NormalProbabilityDesigner {
	
	private double k;
	private double factor;
	private NormalDistribution nd = new NormalDistribution();
	
	public NormalProbabilityDesigner(){
		this(3.5);
	}
	
	public NormalProbabilityDesigner(double k) {
		this.k = k;
	}
	
	public double getFactor() {
		return factor;
	}

	public void setFactor(double factor) {
		this.factor = factor;
	}	

	private double probability(double x0, double x1){
	   return factor * nd.probability(x0, x1);	
	}
	
	public NormalShare getOneSideShare(int rightLength){
		double interval = k / (rightLength + 1);
		
		double[] a = new double[rightLength];
		
		double center = 2*probability(0, interval);
		
		for(int i=0; i < rightLength; i++){
			double x0 = (i+1)*interval;
			double x1 = (i+2)*interval;
			a[i] = 2*probability(x0, x1);
		}
		
		return new NormalShare(new double[0], a, center);
	}
	
	public NormalShare getDoubleSideCenterShare(int leftLength, int rightLength) {
	    int max = Math.max(leftLength, rightLength);
	    
		double interval = k / (2*max +1);
		
		double[] a = new double[max];
		
		double center = probability(-interval, interval);
		
		for(int i=0; i < max; i++){
			double x0 = (2*i+1)*interval;
			double x1 = (2*i+3)*interval;
			a[i] = probability(x0, x1);
		}
		
		int min = Math.min(leftLength, rightLength);
		
		System.out.println("Min "+min+"  Max "+max);
		
		double factor = normalizationFactor(min, interval);
		
		
		normalize(a, factor);
		center = factor * center;
		
		double[] rightArray = Arrays.copyOf(a, rightLength);
		double[] leftArray = Arrays.copyOf(a, leftLength);
		
		for (int i = 0; i < leftArray.length / 2; i++) {
			double temp = leftArray[i];
			leftArray[i] = leftArray[leftArray.length-1-i];
			leftArray[leftArray.length-1-i] = temp;
		}
		
		return new NormalShare(leftArray, rightArray, center);
	}

	private void normalize(double[] a, double f) {
		for (int i = 0; i < a.length; i++) {
			a[i] = f*a[i];
		}
	}

	private double normalizationFactor(int min, double interval) {		
		return 1.0 / nd.probability(-k, (2*min+1)*interval);
	}
}
