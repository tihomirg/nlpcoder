package tweak.randomrefinement;

import commons.ISTextParameterGeneratorAndExampleRunner;

public abstract class TweakRandomWithRefinement {

	protected int randomSteps;
	protected int refinementSteps;
	protected AbstractRandomRefinementIntervalGeneratorFactory rriGenFactory;
	
	public TweakRandomWithRefinement(int randomSteps, int refinementSteps, AbstractRandomRefinementIntervalGeneratorFactory rriGen) {
		this.randomSteps = randomSteps;
		this.refinementSteps = refinementSteps;
		this.rriGenFactory = rriGen;
	}

	public void tweak() {
		for (int i = 0; i <= this.randomSteps; i++) {
			if (i!=0) rriGenFactory.randomizeInterval();
			for (int j = 0; j <= this.refinementSteps; j++) {
				if(j!=0) rriGenFactory.refineInterval();
				ISTextParameterGeneratorAndExampleRunner tds = getExampleRunner();
				tds.tweak();
			}
		}
	}
	
	protected abstract ISTextParameterGeneratorAndExampleRunner getExampleRunner();
		
}
