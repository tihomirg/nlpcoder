package search.scorers;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import search.WToken;
import search.nlp.parser.Subgroup;

public class Bigraph {

	private List<Subgroup> subgroups;
	private List<WToken> declWTokens;
	private double[][] kindWeight;

	public Bigraph(List<Subgroup> subgroups, List<WToken> declWTokens, double[][] kindWeight) {
		this.subgroups = subgroups;
		this.declWTokens = declWTokens;
		this.kindWeight = kindWeight;
	}

	public double calculate(){
		if (this.subgroups.size() == 1){
			return maxOneToNScore(this.subgroups.get(0), declWTokens);
		} else {
			if (this.declWTokens.size() == 1){
				return maxNToOneScore(this.subgroups, declWTokens.get(0));
			} else return maxNToMScore(this.subgroups, this.declWTokens);
		}
	}

	private double maxNToMScore(List<Subgroup> subgroups, List<WToken> declWTokens) {
		int subgroupSize = subgroups.size();		
		int declSize = declWTokens.size();

		double[][] originalMatrix = prepareMaxWeigthBipartateMatrix(subgroups, declWTokens, subgroupSize, declSize);
		double[][] costMatrix = prepareMinWeigthBipartateMatrix(subgroupSize, declSize, originalMatrix);

		HungarianAlgorithm algorithm = new HungarianAlgorithm(costMatrix);
		int[] indexes = algorithm.execute();

		return getWeight(originalMatrix, indexes);
	}

	private double[][] prepareMinWeigthBipartateMatrix(int subgroupSize, int declSize, double[][] originalMatrix) {
		double max = findMaxElement(originalMatrix);
		double[][] costMatrix = new double[subgroupSize][declSize];

		for (int i = 0; i < subgroupSize; i++) {
			for (int j = 0; j < declSize; j++) {
				costMatrix[i][j] = max - originalMatrix[i][j];
			}
		}
		return costMatrix;
	}

	private double[][] prepareMaxWeigthBipartateMatrix(List<Subgroup> subgroups, List<WToken> declWTokens, int subgroupSize, int declSize) {
		double[][] originalMatrix = new double[subgroupSize][declSize];

		for (int i = 0; i < subgroupSize; i++) {
			Subgroup subgroup = subgroups.get(i);
			for (int j = 0; j < declSize; j++) {
				WToken wToken = declWTokens.get(j);
				originalMatrix[i][j] = findMaxEdge(subgroup, wToken);
			}
		}
		return originalMatrix;
	}

	private static double getWeight(double[][] costMatrix, int[] indexes) {
		double sum = 0;
		for (int i = 0; i < costMatrix.length; i++) {
			int index = indexes[i];
			if (index != -1){
				sum += costMatrix[i][index];
			}
		}
		return sum;
	}	

	private static double findMaxElement(double[][] costMatrix) {
		double max = -1;

		for (double[] row : costMatrix) {
			for (double elem : row) {
				if (max < elem){
					max = elem;
				}
			}
		}
		return max;
	}

	public double maxNToOneScore(List<Subgroup> subgorups, WToken wToken) {
		List<Double> scores = new LinkedList<Double>();
		for (Subgroup subgroup : subgorups) {
			scores.add(findMaxEdge(subgroup, wToken));

		}
		return Collections.max(scores);
	}

	public double maxOneToNScore(Subgroup subgroup, List<WToken> declWTokens) {
		List<Double> scores = new LinkedList<Double>();
		for (WToken wToken : declWTokens) {
			scores.add(findMaxEdge(subgroup, wToken));
		}
		return Collections.max(scores);
	}

	public double findMaxEdge(Subgroup subgroup, WToken declWToken) {
		List<Double> scores = new LinkedList<Double>();
		for (WToken wToken : subgroup.getWTokens()) {
			scores.add(edgeWeight(wToken, declWToken));
		}
		return Collections.max(scores);
	}

	public double edgeWeight(WToken inputWToken, WToken declWToken) {
		if(inputWToken.equalsByPosAndLemma(declWToken)){
			//We should keep related score here.
			return inputWToken.getImportanceWeight() * declWToken.getImportanceWeight() * kindWeight(inputWToken.getImportanceIndex(), declWToken.getImportanceIndex()) * inputWToken.getRelatednessWeight();
		} else return 0;
	}

	private double kindWeight(int inputIndex, int declIndex) {
		return kindWeight[inputIndex][declIndex];
	}

}
