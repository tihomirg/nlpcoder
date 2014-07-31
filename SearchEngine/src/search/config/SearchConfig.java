package search.config;

public class SearchConfig {

	private static final int primaryIndex = 0;
	private static final double primaryWeight = 1.0;
	private static final int secondaryIndex = 1;
	private static final double secondaryWeight = 0.5;
	private static final double relatedWeigthFactor = 1.0;

	public static int getPrimaryIndex() {
		return primaryIndex;
	}

	public static double getPrimaryWeight() {
		return primaryWeight;
	}

	public static int getSecondaryIndex() {
		return secondaryIndex;
	}

	public static double getSecondaryWeight() {
		return secondaryWeight;
	}

	public static double getRelatedWeightFactor() {
		return relatedWeigthFactor;
	}

}
