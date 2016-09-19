import java.util.List;

import commons.scores.SingleScore;


public class IterativeGenerator {

	private List<ParameterGenerator> generators;
	private ParameterGenerator curr;
	private int index;
	private SingleScore bestScore;
	
	public void addAll(List<ParameterGenerator> generators) {
		this.generators = generators;
		this.curr = this.generators.get(index);
	}

	public void next() {
		if(curr.hasMore()){
			curr.next();
		} else {
			curr.setBest();
			index = (index + 1) % generators.size();
			curr = generators.get(index);
			curr.reset();
		}
	}
	
	public ParameterGenerator getCurr() {
		return curr;
	}

	public void set() {
		for (ParameterGenerator generator : generators) {
			generator.set();
		}
	}

	public void updateScore(SingleScore score) {
		trySetBestScore(score);
		for (ParameterGenerator generator : generators) {
			generator.updateScore(score);
		}		
	}
	
	private void trySetBestScore(SingleScore score) {
		if (bestScore == null) {
			bestScore = score;
		} else {
			if (score.isBetterThan(bestScore)) {
				this.bestScore = score;
			}
		}
	}

	@Override
	public String toString() {
		return "IterativeGenerator [bestScore=" + bestScore+"\n" 
	            + generatorsToBriefString()+ "]";
	}

	private String generatorsToBriefString() {
		StringBuffer sb = new StringBuffer();
		
		for (ParameterGenerator generator: this.generators) {
			sb.append(generator.toBriefString()+"\n");
		}
		
		return sb.toString();
	}
	
	public int getSize(){
		int size = 0;
		for (ParameterGenerator generator: this.generators) {
			size += generator.getSize();
		}
		return size;
	}
	
	public SingleScore getBestScore() {
		return bestScore;
	}
	
	public List<ParameterGenerator> getGenerators() {
		return generators;
	}

	public void clear() {
		this.bestScore = null;
		this.index = 0;
		this.curr = this.generators.get(index);
		for (ParameterGenerator generator: this.generators) {
			generator.clear();
		}
	}
}
