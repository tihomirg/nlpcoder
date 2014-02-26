package selection.shares;

public class UnitShare implements IShare {

	private double probability;

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public UnitShare(double probability) {
		this.probability = probability;
	}

	@Override
	public double getProbability() {
		return this.probability;
	}
}
