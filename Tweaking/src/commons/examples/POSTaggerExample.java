package commons.examples;

import java.util.LinkedList;
import java.util.List;

import commons.scores.SingleScore;
import commons.values.Value;
import nlp.parser.Token;
import search.ISText;
import api.Local;

public abstract class POSTaggerExample implements Example<Token, Value> {

	protected String input;
	private List<Token> expectedOutput;
	private SingleScore<Value> score;
	protected List<Local> locals;
	
	public POSTaggerExample(String input, List<Token> expectedOutput) {
		this(input, new LinkedList<Local>(), expectedOutput);
	}
	
	public POSTaggerExample(String input, List<Local> locals, List<Token> expectedOutput) {
		this.input = input;
		this.expectedOutput = expectedOutput;
		this.score = new SingleScore<Value>(new Value());
		this.locals = locals;
	}

	@Override
	public List<Token> run(ISText iSText) {
		List<Token> actualOutput = call(iSText);
		if (equals(actualOutput, expectedOutput)) this.score.inc();
		else {
			System.out.println("Missmatch");
			System.out.println("Expected Output: "+expectedOutput);
			System.out.println("Actual Output: "+actualOutput);
		}
		return actualOutput;
	}

	protected abstract List<Token> call(ISText iSText);

	private static boolean equals(List<Token> actualOutput, List<Token> expectedOutput) {
		int size = actualOutput.size();
		if (size != expectedOutput.size()) return false;

		for (int i = 0; i < size; i++) {
			Token actualToken = actualOutput.get(i);
			Token exprectedToken = expectedOutput.get(i);
			
			if(!actualToken.equalsByPosAndLemma(exprectedToken)) return false;
		}
		
		return true;
	}

	@Override
	public SingleScore<Value> getScore() {
		return this.score;
	}
	
	@Override
	public int size() {
		return 0;
	}
	
	@Override
	public String getInput() {
		return this.input;
	}
	
	@Override
	public String getExprectedOuptup() {
		return this.expectedOutput.toString();
	}
}
