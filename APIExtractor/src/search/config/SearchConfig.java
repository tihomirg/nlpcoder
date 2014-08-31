package search.config;

	
public class SearchConfig {
	//Synthesis weights
	private static double partialExpressionBadMergePenalty = 1.0;
	private static double partialExpressionMergeSizePenalty = 0.4;
	private static double partialExpressionMergeReward = 0.2;

	//Individual (partial) expression
	private static double partialExpressionIndividualSizePenalty = 0.4;
	private static int partialExpressionIndividualSize = 2; 

	//Compositions
	private static double compositionWeightFactor = 0.5;
	private static double holeWeight = 0.2;
	
	//Input Exprs
	private static double inputExprRepetitionPenalty = 1.0;
	private static double localVariableWeight = 0.6;
	private static double numberLiteralWeight = 0.6;
	private static double stringLiteralWeight = 0.6;
	private static double booleanLiteralWeight = 0.6;
	
	//Synthesis params
	private static boolean synthesis = true;
	private static boolean parallelSynthesis = true;
	private static int numberOfMergeGroups = 4;
	private static int numberOfSynthesisLevels = 5;
	private static int maxPartialExpressionsPerSynthesisLevel = 5;

	//Declaration Selection and Synthesis params
	private static int maxSelectedDeclarations = 5;

	//Parser params
	private static int inputParserRighHandSideNeighbourNumber = 1;

	//Token weights (for both: input text and declarations)
	private static int primaryIndex = 0;
	private static int secondaryIndex = 1;

	private static double primaryWeight = 0.7;
	private static double secondaryWeight = 0.3;
	private static double relatedWeigthFactor = 1.0;
	
	//Declaration Selection weights
	private static double[][] kindMatrix = new double[][]{{1.0, 0.5},{0.25, 0.5}};
	private static double declarationInputUnmatchingWeight = 0.02;
	private static Double[] declarationScorerCoefs = new Double[]{0.50, 0.50};

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

	public static double getPartialExpressionMergeReward() {
		return partialExpressionMergeReward;
	}

	public static double getPartialExpressionBadMergePenalty() {
		return partialExpressionBadMergePenalty;
	}

	public static double getPartialExpressionMergedSizePenalty() {
		return partialExpressionMergeSizePenalty;
	}

	public static boolean isParallelSynthesis() {
		return parallelSynthesis;
	}

	public static double getLocalVariableWeight() {
		return localVariableWeight;
	}

	public static double getStringLiteralWeight() {
		return stringLiteralWeight;
	}

	public static double getNumberLiteralWeight() {
		return numberLiteralWeight;
	}

	public static boolean isSynthesis() {
		return synthesis;
	}

	public static Double[] getDeclarationScorerCoefs() {
		return declarationScorerCoefs;
	}

	public static double getDeclarationInputUnmatchingWeight() {
		return declarationInputUnmatchingWeight;
	}

	public static double getBooleanLiteralWeight() {
		return booleanLiteralWeight;
	}

	public static double getCompositionWeightFactor() {
		return compositionWeightFactor;
	}

	public static double getHoleWeight() {
		return holeWeight;
	}

	public static int getPartialExpressionIndividualSize() {
		return partialExpressionIndividualSize;
	}

	public static double getPartialExpressionIndividualSizePenalty() {
		return partialExpressionIndividualSizePenalty;
	}

	public static double getInputExprRepetitionPenalty() {
		return inputExprRepetitionPenalty;
	}

	public static void setPartialExpressionBadMergePenalty(
			double partialExpressionBadMergePenalty) {
		SearchConfig.partialExpressionBadMergePenalty = partialExpressionBadMergePenalty;
	}

	public static void setPartialExpressionMergeSizePenalty(
			double partialExpressionMergeSizePenalty) {
		SearchConfig.partialExpressionMergeSizePenalty = partialExpressionMergeSizePenalty;
	}

	public static void setPartialExpressionMergeReward(
			double partialExpressionMergeReward) {
		SearchConfig.partialExpressionMergeReward = partialExpressionMergeReward;
	}

	public static void setPartialExpressionIndividualSizePenalty(
			double partialExpressionIndividualSizePenalty) {
		SearchConfig.partialExpressionIndividualSizePenalty = partialExpressionIndividualSizePenalty;
	}

	public static void setPartialExpressionIndividualSize(
			int partialExpressionIndividualSize) {
		SearchConfig.partialExpressionIndividualSize = partialExpressionIndividualSize;
	}

	public static void setCompositionWeightFactor(double compositionWeightFactor) {
		SearchConfig.compositionWeightFactor = compositionWeightFactor;
	}

	public static void setHoleWeight(double holeWeight) {
		SearchConfig.holeWeight = holeWeight;
	}

	public static void setInputExprRepetitionPenalty(
			double inputExprRepetitionPenalty) {
		SearchConfig.inputExprRepetitionPenalty = inputExprRepetitionPenalty;
	}

	public static void setLocalVariableWeight(double localVariableWeight) {
		SearchConfig.localVariableWeight = localVariableWeight;
	}

	public static void setNumberLiteralWeight(double numberLiteralWeight) {
		SearchConfig.numberLiteralWeight = numberLiteralWeight;
	}

	public static void setStringLiteralWeight(double stringLiteralWeight) {
		SearchConfig.stringLiteralWeight = stringLiteralWeight;
	}

	public static void setBooleanLiteralWeight(double booleanLiteralWeight) {
		SearchConfig.booleanLiteralWeight = booleanLiteralWeight;
	}

	public static void setSynthesis(boolean synthesis) {
		SearchConfig.synthesis = synthesis;
	}

	public static void setParallelSynthesis(boolean parallelSynthesis) {
		SearchConfig.parallelSynthesis = parallelSynthesis;
	}

	public static void setNumberOfMergeGroups(int numberOfMergeGroups) {
		SearchConfig.numberOfMergeGroups = numberOfMergeGroups;
	}

	public static void setNumberOfSynthesisLevels(int numberOfSynthesisLevels) {
		SearchConfig.numberOfSynthesisLevels = numberOfSynthesisLevels;
	}

	public static void setMaxPartialExpressionsPerSynthesisLevel(
			int maxPartialExpressionsPerSynthesisLevel) {
		SearchConfig.maxPartialExpressionsPerSynthesisLevel = maxPartialExpressionsPerSynthesisLevel;
	}

	public static void setMaxSelectedDeclarations(int maxSelectedDeclarations) {
		SearchConfig.maxSelectedDeclarations = maxSelectedDeclarations;
	}

	public static void setInputParserRighHandSideNeighbourNumber(
			int inputParserRighHandSideNeighbourNumber) {
		SearchConfig.inputParserRighHandSideNeighbourNumber = inputParserRighHandSideNeighbourNumber;
	}

	public static void setPrimaryIndex(int primaryIndex) {
		SearchConfig.primaryIndex = primaryIndex;
	}

	public static void setSecondaryIndex(int secondaryIndex) {
		SearchConfig.secondaryIndex = secondaryIndex;
	}

	public static void setPrimaryWeight(double primaryWeight) {
		SearchConfig.primaryWeight = primaryWeight;
	}

	public static void setSecondaryWeight(double secondaryWeight) {
		SearchConfig.secondaryWeight = secondaryWeight;
	}

	public static void setRelatedWeigthFactor(double relatedWeigthFactor) {
		SearchConfig.relatedWeigthFactor = relatedWeigthFactor;
	}

	public static void setKindMatrix(double[][] kindMatrix) {
		SearchConfig.kindMatrix = kindMatrix;
	}

	public static void setDeclarationInputUnmatchingWeight(double declarationInputUnmatchingWeight) {
		SearchConfig.declarationInputUnmatchingWeight = declarationInputUnmatchingWeight;
	}

	public static void setDeclarationScorerCoefs(Double[] declarationScorerCoefs) {
		SearchConfig.declarationScorerCoefs = declarationScorerCoefs;
	}
	
	
}
