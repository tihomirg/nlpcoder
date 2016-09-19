package tweak.synthesis;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import api.Local;
import api.StabileAPI;
import search.ISText;
import search.config.SearchConfig;
import tweak.config.TweakConfig;
import types.StabileTypeFactory;
import types.Type;
import commons.ISTextParameterGeneratorAndExampleRunner;
import commons.examples.Example;
import commons.examples.SynthesisNLExample;
import commons.goals.TopSnippetGoal;
import commons.parameters.DoubleMultiGenerator;
import commons.parameters.IntMultiGenerator;
import commons.parameters.ParameterGenerator;
import commons.strategy.ParameterStrategy;
import commons.values.RankedValue;
import commons.values.Value;

public class PaperExamplesTweakSynthesisNL extends ISTextParameterGeneratorAndExampleRunner<String, RankedValue> {

	private int maxNumberOfSteps;

	public PaperExamplesTweakSynthesisNL(ISText iSText, int maxNumberOfSteps, ParameterStrategy<String, RankedValue> paramStrategy) {
		super(iSText, RankedValue.class, paramStrategy);
		this.maxNumberOfSteps = maxNumberOfSteps;
	}

	public void tweak() {
		int tgs = this.totalExampleGoalSize();
		System.out.println("Total goal size: "+tgs);

		for (int i = 0; i < maxNumberOfSteps; i++) {
			System.out.println(super.run());
		}

		System.out.println(this.paramStrategy);
	}

	@Override
	protected List<Example<String, RankedValue>> getExamples(ISText iSText) {
		List<Example<String, RankedValue>> examples = new LinkedList<Example<String, RankedValue>>();
		StabileAPI api = iSText.getAPI();
		StabileTypeFactory typeFactory = api.getStf();

		Type stringType = typeFactory.createMonomorphicReferenceType(java.lang.String.class);
		Type intType = typeFactory.createPrimitiveType("int");
		Type intArrayType = typeFactory.createArrayType("int");
		Type boolType = typeFactory.createPrimitiveType("boolean");
		Type fileType = typeFactory.createMonomorphicReferenceType(java.io.File.class);
		Type fileReaderType = typeFactory.createMonomorphicReferenceType(java.io.FileReader.class);
		Type panelType = typeFactory.createMonomorphicReferenceType(javax.swing.JPanel.class);
		Type frameType = typeFactory.createMonomorphicReferenceType(javax.swing.JFrame.class);
		Type dateFormatType = typeFactory.createMonomorphicReferenceType(java.text.DateFormat.class);
		Type dateFormatterType = typeFactory.createMonomorphicReferenceType(javax.swing.text.DateFormatter.class);
		Type arrayObjType = typeFactory.createArrayType(java.lang.Object.class);
		Type fisType = typeFactory.createMonomorphicReferenceType(java.io.FileInputStream.class);
		Type fosType = typeFactory.createMonomorphicReferenceType(java.io.FileOutputStream.class);
		Type objType = typeFactory.createMonomorphicReferenceType(java.lang.Object.class);
		Type matrixObjectType = typeFactory.createPolymorphicType("java.lang.Array", new Type[]{arrayObjType});

		Local args = new Local("args", stringType);

		examples.add(createSynthesisNLExample("copy file fname to destination", new Local[]{new Local("fname", stringType), new Local("destination", stringType)}, "FileUtils.copyFile(new File(fname), new File(destination))"));
		examples.add(createSynthesisNLExample("load class \"t\"", "Thread.currentThread().getContextClassLoader().loadClass(\"t\")"));
		examples.add(createSynthesisNLExample("make file", "new File(<arg>).createNewFile()"));
		examples.add(createSynthesisNLExample("does x begin with y", new Local[]{new Local("x", stringType), new Local("y", stringType)}, "x.startsWith(y)"));
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
		return new SynthesisNLExample(input, Arrays.asList(locals), new TopSnippetGoal(input, output));
	}	

	private Example<String, RankedValue> createSynthesisNLExample(String input, String output) {
		return new SynthesisNLExample(input, new TopSnippetGoal(input, output));
	}

	private Example<String, RankedValue> createSynthesisNLExample(String input, Local[] locals, String output) {
		return new SynthesisNLExample(input, Arrays.asList(locals), new TopSnippetGoal(input, output));
	}


	
	@Override
	protected List<ParameterGenerator<?>> getParameterGenerators() {
		List<ParameterGenerator<?>> generators = new LinkedList<ParameterGenerator<?>>();

		generators.add(new IntMultiGenerator("PartialExpressionIndividualSize", 3, 1, 1) {
			@Override
			public void set() {
				SearchConfig.setPartialExpressionIndividualSize(this.getVal());
			}
		});

		generators.add(new DoubleMultiGenerator("PartialExpressionBadMergePenalty", 0.5, 0.1, 4) {
			@Override
			public void set() {
				SearchConfig.setPartialExpressionBadMergePenalty(this.getVal());
			}
		});

		generators.add(new DoubleMultiGenerator("PartialExpressionIndividualSizePenalty", 0.5, 0.1, 4) {
			@Override
			public void set() {
				SearchConfig.setPartialExpressionIndividualSizePenalty(this.getVal());
			}
		});
		
		generators.add(new DoubleMultiGenerator("PartialExpressionMergeReward", 0.5, 0.1, 4) {
			@Override
			public void set() {
				SearchConfig.setPartialExpressionMergeReward(this.getVal());
			}
		});

		generators.add(new DoubleMultiGenerator("PartialExpressionMergeSizePenalty", 0.5, 0.1, 4) {
			@Override
			public void set() {
				SearchConfig.setPartialExpressionMergeSizePenalty(this.getVal());
			}
		});

		generators.add(new DoubleMultiGenerator("HoleWeight", 0.05, 0.01, 4) {
			@Override
			public void set() {
				// TODO Auto-generated method stub
				SearchConfig.setHoleWeight(this.getVal());
			}
		});

		generators.add(new DoubleMultiGenerator("InputExprRepetitionPenalty", 0.5, 0.1, 4) {
			@Override
			public void set() {
				SearchConfig.setInputExprRepetitionPenalty(this.getVal());

			}
		});

		generators.add(new DoubleMultiGenerator("CompositionWeightFactor", 0.5, 0.1, 4) {
			@Override
			public void set() {
				SearchConfig.setCompositionWeightFactor(this.getVal());
			}
		});

		generators.add(new DoubleMultiGenerator("InputExprWeight", 0.5, 0.1, 4) {
			@Override
			public void set() {
				double value = this.getVal();
				SearchConfig.setBooleanLiteralWeight(value);
				SearchConfig.setNumberLiteralWeight(value);
				SearchConfig.setStringLiteralWeight(value);
				SearchConfig.setLocalVariableWeight(value);
			}
		});

		return generators;
	}

	public static void main(String[] args) {
		TweakConfig config = TweakConfig.getInstance();
		SearchConfig.setMaxSelectedDeclarations(config.getMaxSelectedDeclarations());

		SearchConfig.setDeclarationInputUnmatchingWeight(0.0);
		SearchConfig.setPrimaryWeight(0.7);
		SearchConfig.setSecondaryWeight(0.3);
		SearchConfig.setRelatedWeigthFactor(0.9);
		SearchConfig.setDeclarationScorerCoefs(new Double[]{0.7, 0.3});
		SearchConfig.setKindMatrix(new double[][]{{0.7, 0.3},{0.4, 0.5}});

		PaperExamplesTweakSynthesisNL tsnl = new PaperExamplesTweakSynthesisNL(new ISText(config.getSnippetNumISText()), 
				config.getIterationNum(), config.<String, RankedValue>getStrategy());
		tsnl.tweak();
	}
}
