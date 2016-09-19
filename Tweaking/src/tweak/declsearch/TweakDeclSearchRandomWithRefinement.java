package tweak.declsearch;

import commons.ISTextParameterGeneratorAndExampleRunner;
import commons.strategy.ParameterStrategy;
import commons.strategy.RoundRobinStrategy;
import commons.values.RankedValue;
import search.ISText;
import search.SearchReport;
import tweak.config.TweakConfig;
import tweak.randomrefinement.AbstractRandomRefinementIntervalGeneratorFactory;
import tweak.randomrefinement.TweakRandomWithRefinement;

public class TweakDeclSearchRandomWithRefinement extends TweakRandomWithRefinement {

	private ISText iSText;
	private int maxNumberOfSteps;
	private ParameterStrategy<SearchReport, RankedValue> strategy;

	public TweakDeclSearchRandomWithRefinement(
			int randomSteps, 
			int refinementSteps, 
			AbstractRandomRefinementIntervalGeneratorFactory rriGenFactory, 
			ISText iSText,
			int maxNumberOfSteps,
			ParameterStrategy<SearchReport, RankedValue> strategy) {
		super(randomSteps, refinementSteps, rriGenFactory);
		this.iSText = iSText;
		this.maxNumberOfSteps = maxNumberOfSteps;
		this.strategy = strategy;
	}

	@Override
	protected ISTextParameterGeneratorAndExampleRunner<SearchReport, RankedValue> getExampleRunner() {
		return new TweakDeclSearchWithParametrizedIntervals(this.iSText, this.maxNumberOfSteps, this.strategy, super.rriGenFactory);
	}
	
	public static void main(String[] args) {
		TweakConfig config = TweakConfig.getInstance();
		TweakDeclSearchRandomWithRefinement tdsrr = new TweakDeclSearchRandomWithRefinement(
				2, 2, new DeclSearchParameterIntervalGeneratorFactory(),
				new ISText(config.getSnippetNumISText()), config.getIterationNum(),
				config.<SearchReport, RankedValue>getStrategy());
		
		tdsrr.tweak();
	}
}
