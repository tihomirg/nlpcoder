package tweak.randomrefinement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tweak.intervals.Interval;
import tweak.intervals.IntervalValue;

public abstract class AbstractRandomRefinementIntervalGeneratorFactory {

	private List<RandomRefinementIntervalGenerator<?>> generators;
	private Map<String, RandomRefinementIntervalGenerator<?>> intervalMap;

	public AbstractRandomRefinementIntervalGeneratorFactory() {
		this.generators = getRandomRefinementIntervals();
		this.intervalMap = createMap(generators);
	}
	
	private Map<String, RandomRefinementIntervalGenerator<?>> createMap(List<RandomRefinementIntervalGenerator<?>> generators) {
		Map<String, RandomRefinementIntervalGenerator<?>> map = new HashMap<String, RandomRefinementIntervalGenerator<?>>();
		for (RandomRefinementIntervalGenerator<?> generator : generators) {
			map.put(generator.getName(), generator);
		}
		return map;
	}

	public void randomizeInterval() {
		for (RandomRefinementIntervalGenerator<?> generator : generators) {
			generator.randomize();
		}
	}

	public void refineInterval() {
		for (RandomRefinementIntervalGenerator<?> generator : generators) {
			generator.refine();
		}
	}
	
	public abstract List<RandomRefinementIntervalGenerator<?>> getRandomRefinementIntervals();
	
	public <T extends IntervalValue> RandomRefinementIntervalGenerator<T> getIntervalGenerator(String name){
		return (RandomRefinementIntervalGenerator<T>) intervalMap.get(name);
	}
}
