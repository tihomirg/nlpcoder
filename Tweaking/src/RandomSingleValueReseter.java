import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;


public class RandomSingleValueReseter {

	private static final ResultDescComparator comparator = new ResultDescComparator();
	private IterativeGenerator generator;
	private PriorityQueue<Result> results;

	public RandomSingleValueReseter(IterativeGenerator generator) {
		this.generator = generator;
		this.results = new PriorityQueue<Result>(100, comparator);
	}

	public void reset(){
		saveResult();
		setIndividualGenerators();
	}

	private void saveResult() {
		this.results.add(new Result(generator.getBestScore(), generator.getGenerators()));
	}

	private void setIndividualGenerators() {
		this.generator.clear();
		
		List<ParameterGenerator> generators = this.generator.getGenerators();
		
		for (ParameterGenerator generator : generators) {
			generator.setUniqueValue(Math.random());
		}
	}

	public Result getBestScore() {
		return this.results.remove();
	}
}
