package selection;

import java.util.Arrays;

import org.apache.commons.math3.distribution.NormalDistribution;

public class NoramalProbabilityDesigner {
	
	private double k = 3.5;
	private NormalDistribution nd = new NormalDistribution();
	
	public NormalShare getOnesideShare(int rightLength){
		
		double length = rightLength + 1;
		double interval = k / length;
		
		double[] a = new double[rightLength];
		
		double center = 2*nd.probability(0, interval);
		
		for(int i=1; i < length; i++){
			double x0 = i*interval;
			double x1 = (i+1)*interval;
			a[i] = 2*nd.probability(x0, x1);
		}
		
		return new NormalShare(new double[0], a, center);
	}
	
	public NormalShare getDoubleSideCenterShare(int leftLength, int rightLength) {
	    int max = Math.max(leftLength, rightLength);
	    
		double interval = k / (2*max +1);
		
		double[] a = new double[max];
		
		double center = nd.probability(-interval, interval);
		
		for(int i=0; i < max; i++){
			double x0 = (2*i+1)*interval;
			double x1 = (2*i+3)*interval;
			a[i] = nd.probability(x0, x1);
		}
		
		int min = Math.min(leftLength, rightLength);
		double factor = normalizaationFactor(min, interval);
		normalize(a, factor);
		center = factor * center;
		return new NormalShare(Arrays.copyOf(a, leftLength), Arrays.copyOf(a, rightLength), center);
	}

	private void normalize(double[] a, double f) {
		for (int i = 0; i < a.length; i++) {
			a[i] = f*a[i];
		}
	}

	private double normalizaationFactor(int min, double interval) {
		return 1.0 / nd.probability((2*min+1)*interval);
	}
}
