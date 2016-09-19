package results;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import search.ISText;
import search.config.SearchConfig;
import types.StabileTypeFactory;
import types.Type;
import api.Local;
import api.StabileAPI;
import commons.Evaluator;
import commons.examples.Example;
import commons.examples.SynthesisNLExample;
import commons.goals.PresentationTopSnippetGoal;
import commons.values.RankedValue;

public class PaperExampleRunner {
	
	protected static final Local[] EMPTY_LOCALS = new Local[0];
	protected List<Example<String, RankedValue>> examples;
	protected Evaluator<String, RankedValue> eval;
	
	public PaperExampleRunner(ISText iSText) {
		this.examples = getExamples(iSText);
		this.eval = new Evaluator<String, RankedValue>(iSText, RankedValue.class, examples);
	}
	
	public void run() {
		List<List<String>> resultss = eval.run();
		for (int i=0; i < resultss.size(); i++) {
			List<String> results = resultss.get(i);
			Example<String, RankedValue> example = examples.get(i);
			
			System.out.println();
			System.out.println(example.getInput());
			System.out.println();
			for (int j=0; j < results.size(); j++) {
				System.out.println((j+1)+" : "+results.get(j));
			}
			System.out.println();
		}
	}
	
	protected List<Example<String, RankedValue>> getExamples(ISText iSText) {
		List<Example<String, RankedValue>> examples = new LinkedList<Example<String, RankedValue>>();
		StabileAPI api = iSText.getAPI();
		StabileTypeFactory typeFactory = api.getStf();

		Type stringType = typeFactory.createMonomorphicReferenceType(java.lang.String.class);

		//Paper examples
		examples.add(createSynthesisNLExample("copy file fname to destination", new Local[]{new Local("fname", stringType), new Local("destination", stringType)}, "FileUtils.copyFile(new File(fname), new File(destination))"));
		examples.add(createSynthesisNLExample("does x begin with y", new Local[]{new Local("x", stringType), new Local("y", stringType)}, "x.startsWith(y)"));
		examples.add(createSynthesisNLExample("load class \"t\"", "Thread.currentThread().getContextClassLoader().loadClass(\"t\")"));
		examples.add(createSynthesisNLExample("make file", "new File(<arg>).createNewFile()"));
		examples.add(createSynthesisNLExample("write \"t1\" to file \"t2\"", "FileUtils.writeStringToFile(new File(\"t2\"), \"t1\")"));
		examples.add(createSynthesisNLExample("readFile(\"t1\",\"t2\")", "FileUtils.readFileToString(new File(\"t1\"), \"t2\")"));

		return examples;
	}

	private Example<String, RankedValue> createSynthesisNLExample(Class<?> expectedClass, String output){
		return createSynthesisNLExample(expectedClass, "", output);
	}

	private Example<String, RankedValue> createSynthesisNLExample(Class<?> expectedClass, String inputSufix, String output){
		return createSynthesisNLExample(expectedClass, inputSufix, EMPTY_LOCALS, output);
	}

	private Example<String, RankedValue> createSynthesisNLExample(Class<?> expectedClass, String inputSufix, Local[] locals, String output) {
		String input = "new"+expectedClass.getSimpleName()+" "+inputSufix;
		return new SynthesisNLExample(input, Arrays.asList(locals), new PresentationTopSnippetGoal(input, output));
	}	

	private Example<String, RankedValue> createSynthesisNLExample(String input, String output) {
		return new SynthesisNLExample(input, new PresentationTopSnippetGoal(input, output));
	}

	private Example<String, RankedValue> createSynthesisNLExample(String input, Local[] locals, String output) {
		return new SynthesisNLExample(input, Arrays.asList(locals), new PresentationTopSnippetGoal(input, output));
	}	

	public static void main(String[] args) {
		SearchConfig.setPartialExpressionBadMergePenalty(1.6);
		SearchConfig.setPartialExpressionMergeSizePenalty(0.5);
		SearchConfig.setPartialExpressionMergeReward(0.0);
		SearchConfig.setPartialExpressionIndividualSize(2);
		SearchConfig.setPartialExpressionIndividualSizePenalty(0.3);

		SearchConfig.setHoleWeight(0.00);
		SearchConfig.setInputExprRepetitionPenalty(0.9);
		SearchConfig.setCompositionWeightFactor(0.6);

		double inputExprWeight = 1.9;
		SearchConfig.setLocalVariableWeight(inputExprWeight);
		SearchConfig.setNumberLiteralWeight(inputExprWeight);
		SearchConfig.setStringLiteralWeight(inputExprWeight);
		SearchConfig.setBooleanLiteralWeight(inputExprWeight);

		SearchConfig.setDeclarationInputUnmatchingWeight(0.0);
		SearchConfig.setPrimaryWeight(0.7);
		SearchConfig.setSecondaryWeight(0.3);
		SearchConfig.setRelatedWeigthFactor(0.9);
		SearchConfig.setDeclarationScorerCoefs(new Double[]{0.7, 0.3});
		SearchConfig.setKindMatrix(new double[][]{{0.7, 0.3},{0.4, 0.5}});		

		SearchConfig.setMaxSelectedDeclarations(10);		
		
		PaperExampleRunner runner = new PaperExampleRunner(new ISText(10));
		runner.run();
	}
}
