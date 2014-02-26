package selection.shares;

public class UniformShare implements IShare {
	private int parties;
	private IShare share;
	private double unitProbability;
	
	public UniformShare(int parties, double totalShare){
		this(parties, ShareFactory.getUnitShare(totalShare));
	}
	
	public UniformShare(int parties, IShare totalShare) {
		this.parties = parties;
		this.share = totalShare;
		this.unitProbability = share.getProbability()/parties;
	}
	
	public double getProbability() {
		return this.unitProbability;
	}
	
	public IShare getUnitShare(){
		return ShareFactory.getUnitShare(this.unitProbability);
	}

	public int getParties() {
		return parties;
	}

	public void setParties(int parties) {
		this.parties = parties;
	}
}
