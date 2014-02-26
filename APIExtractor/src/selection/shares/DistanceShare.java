package selection.shares;

public class DistanceShare implements IShare {
	private static double ALPHA = 0.5;
	private double alpha = ALPHA;
	private IShare share;
	private int distance = -1;
	private double prob;
	
	private DistanceShare(int distance, double totalShare){
		this(distance, ShareFactory.getUnitShare(totalShare));
	}
	
	private DistanceShare(int distance, IShare share){
		this.distance = distance;
		this.share = share;
		this.prob = (share.getProbability()) * Math.pow(ALPHA, distance+1);
	}
	
	public double getProbability() {
		return this.prob;
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public IShare getShare() {
		return share;
	}

	public void setShare(IShare share) {
		this.share = share;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
}
