package selection;

public class NormalShare {
	private double[] left;
	private double[] right;
	private double center;
		
	public NormalShare(double[] left, double[] right, double center) {
		this.left = left;
		this.right = right;
		this.center = center;
	}
	
	public double[] getLeft() {
		return left;
	}
	public void setLeft(double[] left) {
		this.left = left;
	}
	public double[] getRight() {
		return right;
	}
	public void setRight(double[] right) {
		this.right = right;
	}
	public double getCenter() {
		return center;
	}
	public void setCenter(double center) {
		this.center = center;
	}
	
	public double[] toArray(){
		double[] a = new double[left.length + right.length+1];
		
		for (int i = 0; i < left.length; i++) {
			a[i] = left[i];
		}
		
		a[left.length] = center;
		
		for (int i = left.length+1; i < right.length+left.length+1; i++) {
			a[i] = right[i];
		}
		
		return a;
	}
	
}
