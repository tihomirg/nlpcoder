package tweak.config;

import commons.strategy.ParameterStrategy;
import commons.strategy.RandomStrategy;
import commons.strategy.RoundRobinStrategy;
import commons.values.Ordered;

public class TweakConfig {

	private static TweakConfig instance = new TweakConfig();
	private static final int RANDOM_STRATEGY = 0;
	private static final int ROUNDROBIN_STRATEGY = 1;
	private int strategy = RANDOM_STRATEGY;	
	private int snippetNumISText = 100; 
	private int iterationNum = 200;
	private int maxSelectedDeclarations = 10;
	
	private TweakConfig() {
	}

	public static TweakConfig getInstance(){
		 return instance;
	}

	public <T, V extends Ordered<V>> ParameterStrategy<T, V> getStrategy() {
		switch(this.strategy){
		case RANDOM_STRATEGY:
			return new RandomStrategy<T, V>();
		case ROUNDROBIN_STRATEGY:
			return new RoundRobinStrategy<T, V>();
		default:
			return null;
		}
	}

	public int getSnippetNumISText() {
		return snippetNumISText;
	}

	public int getIterationNum() {
		return iterationNum;
	}

	public int getMaxSelectedDeclarations() {
		return this.maxSelectedDeclarations;
	}
}
