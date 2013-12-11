//TODO: Should be removed

package rules;
public class PackedRule {
	private String right;
	private String left;
	private int frequency;
	private int totalFrequency;
	private double probability;
	private double logProbability;
	
	public PackedRule(String right, String left, int frequency, int totalFrequency){
		this.right = right;
		this.left = left;
		this.frequency = frequency;
		this.totalFrequency = totalFrequency;
		assert totalFrequency > 0;
		this.probability = ((double) frequency) / totalFrequency;
		this.logProbability = Math.log((double) frequency) - Math.log((double) totalFrequency);
	}
	
	public String getRight() {
		return right;
	}

	public void setRight(String right) {
		this.right = right;
	}

	public String getLeft() {
		return left;
	}

	public void setLeft(String left) {
		this.left = left;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	public int getTotalFrequency() {
		return totalFrequency;
	}

	public void setTotalFrequency(int totalFrequency) {
		this.totalFrequency = totalFrequency;
	}
	
	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public double getLogProbability() {
		return logProbability;
	}

	public void setLogProbability(double logProbability) {
		this.logProbability = logProbability;
	}	
	
	public String toString(){
	  return toStringFrequency();
	}
	
	private String toStringProbability(){
	  return frequency+" ("+probability+") : "+right+ " -> "+left;	
	}
	
	private String toStringFrequency(){
	  return frequency+" : "+right+ " -> "+left;	
	}

}
