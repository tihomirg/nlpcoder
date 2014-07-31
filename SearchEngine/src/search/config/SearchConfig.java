package search.config;

public class SearchConfig {
	
	//Synthesis weights
	private static final int partialExpressionConnectorPenalty = 3;
	private static final int partialExpressionConnectorReward = 3;
	private static final int partialExpressionSizePenalty = 3;
	private static final int localVariableWeight = 2;
	private static final int numberLiteralWeight = 2;
	private static final int stringLiteralWeight = 2;
	private static final int literalRepetitionPenalty = 2;
	
	//Synthesis params
	private static final boolean parallelSynthesis = false;
	private static final int numberOfMergeGroups = 4;
	private static final int numberOfSynthesisLevels = 5;
	private static final int maxPartialExpressionsPerSynthesisLevel = 10;

	//Declaration Selection and Synthesis params
	private static final int maxSelectedDeclarations = 10;

	//Parser params
	private static final int inputParserRighHandSideNeighbourNumber = 1;

	//Token weights (for both: input text and declarations)
	private static final int primaryIndex = 0;
	private static final double primaryWeight = 1.0;
	private static final int secondaryIndex = 1;
	private static final double secondaryWeight = 0.5;
	private static final double relatedWeigthFactor = 1.0;
	
	//Declaration Selection weights
	private static final double declarationUnigramFactor = 0.5;
	private static final double[][] kindMatrix = new double[][]{{1, 0.5},{0.5, 1}};

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

	public static double getDeclarationUnigramFactor() {
		return declarationUnigramFactor;
	}

	public static double[][] getDeclarationInputKindMatrix() {
		return kindMatrix;
	}

	public static int getInputParserRighHandSideNeighbourNumber() {
		return inputParserRighHandSideNeighbourNumber;
	}

	public static int getMaxSelectedDeclarations() {
		return maxSelectedDeclarations;
	}

	public static int getMaxPartialExpressionsPerSynthesisLevel() {
		return maxPartialExpressionsPerSynthesisLevel;
	}

	public static int getNumberOfSynthesisLevels() {
		return numberOfSynthesisLevels;
	}

	public static int getNumberOfMergeGroups() {
		return numberOfMergeGroups;
	}

	public static double getPartialExpressionConnectorReward() {
		return partialExpressionConnectorReward;
	}

	public static double getPartialExpressionConnectorPenalty() {
		return partialExpressionConnectorPenalty;
	}

	public static double getPartialExpressionSizePenalty() {
		return partialExpressionSizePenalty;
	}

	public static boolean isParallelSynthesis() {
		return parallelSynthesis;
	}

	public static double getLocalVariableWeight() {
		return localVariableWeight;
	}

	public static int getLiteralRepetitionPenalty() {
		return literalRepetitionPenalty;
	}

	public static double getStringLiteralWeight() {
		return stringLiteralWeight;
	}

	public static double getNumberLiteralWeight() {
		return numberLiteralWeight;
	}

}
