import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;


public class RandomReseter {

	private static final ResultDescComparator comparator = new ResultDescComparator();
	private IterativeGenerator generator;
	private PriorityQueue<Result> results;

	public RandomReseter(IterativeGenerator generator) {
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
			int size = generator.getSize();
			generator.setBest(getRandom(size));
		}
	}

	private int getRandom(int size) {
		return (int) Math.floor(Math.random() * size);
	}

	public Result getBestScore() {
		return this.results.remove();
	}
}
