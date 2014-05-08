package selection;

public class ScoreDesigner {
	private double[] scores;

	public ScoreDesigner(double[] scores) {
		this.scores = scores;
	}

	public double[] getScores(int firstImporatantIndex, int lastImportantIndex, int length) {	
		double[] result = new double[length];

		for(int i=firstImporatantIndex; i <= lastImportantIndex; i++){
			result[i] = scores[0];
		}

		int rightLength = length-(lastImportantIndex+1);

		if(rightLength > 0){
			System.arraycopy(scores, 1, result, (lastImportantIndex+1), rightLength);		
		}

		if(firstImporatantIndex > 0){
			double[] left = new double[firstImporatantIndex];
			System.arraycopy(scores, 1, left, 0, firstImporatantIndex);
			System.arraycopy(inverse(left), 0, result, 0, firstImporatantIndex);
		}
		
		return result;
	}

	private double[] inverse(double[] old) {
		int length = old.length;
		double[] array = new double[length];
		for (int i = 0; i < length; i++) {
			array[i] = old[length-1-i];
		}
		return array;
	}
}
