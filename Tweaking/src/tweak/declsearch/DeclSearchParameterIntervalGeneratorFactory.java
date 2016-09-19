package tweak.declsearch;

import java.util.LinkedList;
import java.util.List;

import commons.parameters.DoubleMultiGenerator;
import search.config.SearchConfig;
import tweak.intervals.DoubleIntervalValue;
import tweak.intervals.Interval;
import tweak.randomrefinement.AbstractRandomRefinementIntervalGeneratorFactory;
import tweak.randomrefinement.RandomRefinementIntervalGenerator;

public class DeclSearchParameterIntervalGeneratorFactory extends AbstractRandomRefinementIntervalGeneratorFactory {

	@Override
	public List<RandomRefinementIntervalGenerator<?>> getRandomRefinementIntervals() {
		List<RandomRefinementIntervalGenerator<?>> generators = new LinkedList<RandomRefinementIntervalGenerator<?>>();

		DoubleIntervalValue _0_5 = new DoubleIntervalValue(0.5);
		DoubleIntervalValue _0_2 = new DoubleIntervalValue(0.2);
		DoubleIntervalValue _0_1 = new DoubleIntervalValue(0.1);
		DoubleIntervalValue _0_01 = new DoubleIntervalValue(0.01);
		DoubleIntervalValue _0_05 = new DoubleIntervalValue(0.05);

		generators.add(new RandomRefinementIntervalGenerator<DoubleIntervalValue>(
				"kindMatrix[0][0]", new Interval<DoubleIntervalValue>(_0_5, _0_1, 2), _0_5, _0_1, 4, 2));

		
		generators.add(new RandomRefinementIntervalGenerator<DoubleIntervalValue>(
				"kindMatrix[0][1]", new Interval<DoubleIntervalValue>(_0_2, _0_1, 0), _0_2, _0_1, 0, 2));

		generators.add(new RandomRefinementIntervalGenerator<DoubleIntervalValue>(
				"kindMatrix[1][0]", new Interval<DoubleIntervalValue>(_0_2, _0_1, 0), _0_2, _0_1, 0, 2));

		generators.add(new RandomRefinementIntervalGenerator<DoubleIntervalValue>(
				"kindMatrix[1][1]", new Interval<DoubleIntervalValue>(_0_5, _0_1, 2), _0_5, _0_1, 4, 2));

		generators.add(new RandomRefinementIntervalGenerator<DoubleIntervalValue>(
				"DeclarationInputUnmatchingWeight", new Interval<DoubleIntervalValue>(_0_05, _0_01, 2), _0_05, _0_01, 4, 2));

		generators.add(new RandomRefinementIntervalGenerator<DoubleIntervalValue>(
				"DeclarationScorerCoefs", new Interval<DoubleIntervalValue>(_0_5, _0_1, 2), _0_5, _0_1, 4, 2));

		generators.add(new RandomRefinementIntervalGenerator<DoubleIntervalValue>(
				"PrimaryWeight", new Interval<DoubleIntervalValue>(_0_5, _0_1, 2), _0_5, _0_1, 4, 2));
		generators.add(new RandomRefinementIntervalGenerator<DoubleIntervalValue>(
				"RelatedWeigthFactor", new Interval<DoubleIntervalValue>(_0_5, _0_1, 2), _0_5, _0_1, 4, 2));

		return generators;
	}

}
