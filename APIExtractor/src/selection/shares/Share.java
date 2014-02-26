package selection.shares;

public class Share {
	private int parties;
	private double totalShare;
	
	public Share(int parties, double totalShare) {
		this.parties = parties;
		this.totalShare = totalShare;
	}
	
	public double getProbability() {
		return totalShare/parties;
	}
}
