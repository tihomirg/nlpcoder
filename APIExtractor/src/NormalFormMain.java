import java.util.Arrays;

import org.apache.commons.math3.distribution.NormalDistribution;


public class NormalFormMain {

	public static void main(String[] args){
		NormalDistribution nd = new NormalDistribution();
		
		double k = 3.0;
		int n = 2;
		double interval = k / (2*n +1);
		
		double[] a = new double[n];
		
		double c = nd.probability(-interval, interval);
		
		for(int i=0; i < n; i++){
			double x0 = (2*i+1)*interval;
			double x1 = (2*i+3)*interval;
			a[i] = nd.probability(x0, x1);
		}
		
		System.out.println(c);
		System.out.println(Arrays.toString(a));

	}
}
