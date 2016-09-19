import java.util.LinkedList;
import java.util.List;

import commons.scores.SingleScore;
import commons.values.Value;
import api.Local;
import search.ISText;


public class Evaluator {

	private static final List<Local> locals = new LinkedList<Local>();
	private ISText isText;
	private List<Example> tests;

	private Goal individualGoal;
	private boolean print;
	
	public Evaluator(ISText isText, boolean print) {
		this.isText = isText;
		this.print = print;
	}

	public SingleScore run() {
		SingleScore compositeScore = new SingleScore(new Value());
		
		for (Example test : tests) {
			compositeScore.add(run(test, individualGoal));
		}
		
		return compositeScore;
	}

	private SingleScore run(Example test, Goal individualGoal) {
		String[] results = this.isText.run(test.getInput(), locals);

		//print(results);
		
		int index = tryFindIndex(results, test.getOutput());
		return new SingleScore<Value>(new Value(individualGoal.evaluate(index)));
	}

	private void print(String[] results) {
		if (this.print){
			System.out.println();
			for (String result : results) {
				System.out.println(result);
			}
			System.out.println();
		}
	}

	private int tryFindIndex(String[] results, String output) {
		for (int i = 0; i < results.length; i++) {
			if(results[i].equals(output)) return i;
		}
		return -1;
	}

	public void setIndividualGoal(Goal goal) {
		this.individualGoal = goal;
	}

	public void addTests(List<Example> tests) {
		this.tests = tests;
	}

}
