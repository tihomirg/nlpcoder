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

public class ISTextTweakSynthesisNL extends ISTextParameterGeneratorAndExampleRunner<String, RankedValue> {

	private int maxNumberOfSteps;

	public ISTextTweakSynthesisNL(ISText iSText, int maxNumberOfSteps, ParameterStrategy<String, RankedValue> paramStrategy) {
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

		examples.add(createSynthesisNLExample("copy file fname to destination", new Local[]{new Local("fname", stringType), new Local("destination", stringType)}, "FileUtils.copyFile(new File(fname), new File(destination))"));
		examples.add(createSynthesisNLExample("does x begin with y", new Local[]{new Local("x", stringType), new Local("y", stringType)}, "x.startsWith(y)"));
		examples.add(createSynthesisNLExample("load class \"t\"", "Thread.currentThread().getContextClassLoader().loadClass(\"t\")"));
		examples.add(createSynthesisNLExample("make file", "new File(<arg>).createNewFile()"));
		examples.add(createSynthesisNLExample("write \"t1\" to file \"t2\"", "FileUtils.writeStringToFile(new File(\"t2\"), \"t1\")"));
		examples.add(createSynthesisNLExample("readFile(\"t1\",\"t2\")", "FileUtils.readFileToString(new File(\"t1\"), \"t2\")"));
		
		examples.add(createSynthesisNLExample("open file \"t\"", "FileUtils.openOutputStream(new File(\"t\"))"));
		examples.add(createSynthesisNLExample("read file \"t\"", "FileUtils.readFileToString(new File(\"t\"))"));
		examples.add(createSynthesisNLExample("parse \"t\"", "Integer.parseInt(\"t\")"));
		examples.add(createSynthesisNLExample("substring \"t\" 1", "\"t\".substring(1)"));
		examples.add(createSynthesisNLExample("new buffered stream \"t\"", "new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(\"t\"))))"));
		examples.add(createSynthesisNLExample("get a current year from a calendar", "new GregorianCalendar().get(Calendar.YEAR)"));
		examples.add(createSynthesisNLExample("leap year", "new GregorianCalendar().isLeapYear(Calendar.getInstance().get(Calendar.YEAR))"));
		examples.add(createSynthesisNLExample("current time", "System.currentTimeMillis()"));
		examples.add(createSynthesisNLExample("open connection \"t\"", "new URL(\"t\").openConnection()"));

		examples.add(createSynthesisNLExample("new socket \"t\" 80", "new socket \"t\" 80"));
		examples.add(createSynthesisNLExample("put \"t1\" \"t2\" pair into a map", "new HashMap().put(\"t1\", \"t2\")"));
		examples.add(createSynthesisNLExample("set thread max priority", "Thread.currentThread().setPriority(Thread.MAX_PRIORITY)"));
		examples.add(createSynthesisNLExample("get property \"t\"", "new Properties().getProperty(\"t\")"));
		examples.add(createSynthesisNLExample("does a file \"t\" exists", "new File(\"t\").exists()"));
		examples.add(createSynthesisNLExample("min 1 3", "Math.min(1, 3)"));
		examples.add(createSynthesisNLExample("get thread id", "Thread.currentThread().getId()"));
		examples.add(createSynthesisNLExample("join threads", "Thread.currentThread().join()"));
		examples.add(createSynthesisNLExample("delete file", "new File(\"t\").delete()"));
		examples.add(createSynthesisNLExample("print exception stack trace", "new Exception(\"t\").printStackTrace(System.out)"));

		examples.add(createSynthesisNLExample("is file \"t\" directory", "new File(\"t\").isDirectory()"));
		examples.add(createSynthesisNLExample("get thread stack trace", "Thread.currentThread().getStackTrace()"));
		examples.add(createSynthesisNLExample("read file \"t\", line by line", "new LineNumberReader(new FileReader(\"t\")).readLine()"));
		examples.add(createSynthesisNLExample("write \"t1\" to a file \"t2\"", "new FileWriter(new File(\"t2\")).write(\"t1\")"));
		examples.add(createSynthesisNLExample("set time zone to \"t\"", "Calendar.getInstance().setTimeZone(TimeZone.getTimeZone(\"t\"))"));
		examples.add(createSynthesisNLExample("empty map", "Collections.emptyMap()"));
		examples.add(createSynthesisNLExample("pi", "Math.PI"));
		examples.add(createSynthesisNLExample("split \"t1\" with \"t2\"", "\"t1\".split(\"t2\")"));
		examples.add(createSynthesisNLExample("memory", "Runtime.getRuntime().freeMemory()"));

		examples.add(createSynthesisNLExample("free memory", "Runtime.getRuntime().freeMemory()"));
		examples.add(createSynthesisNLExample("total memory", "Runtime.getRuntime().totalMemory()"));
		examples.add(createSynthesisNLExample("exec \"t\"", "Runtime.getRuntime().exec(\"t\")"));
		examples.add(createSynthesisNLExample("new data stream \"t\"", "new DataInputStream(new FileInputStream(\"t\"))"));
		examples.add(createSynthesisNLExample("rename file \"t1\" to \"t2\"", "new File(\"t1\").renameTo(new File(\"t2\"))"));
		examples.add(createSynthesisNLExample("move file \"t1\" to \"t2\"", "FileUtils.moveFile(new File(\"t1\"), new File(\"t2\"))"));
		examples.add(createSynthesisNLExample("concat \"t1\" \"t2\"", "\"t1\".concat(\"t2\")"));
		examples.add(createSynthesisNLExample("write a utf \"t1\" to a file \"t2\"", "new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(\"t2\")))).writeUTF(\"t1\")"));
		examples.add(createSynthesisNLExample("java home", "SystemUtils.getJavaHome()"));		

		examples.add(createSynthesisNLExample("\"t\" lower case", "t.toLowerCase()"));
		examples.add(createSynthesisNLExample("upper(\"t\")", "\"t\".toUpperCase()"));
		examples.add(createSynthesisNLExample("compare \"t1\" \"t2\"","\"t1\".compareToIgnoreCase(\"t2\")"));
		examples.add(createSynthesisNLExample("BufferedInput \"t\"", "new BufferedInputStream(new FileInputStream(\"t\"))"));
		examples.add(createSynthesisNLExample("set thread min priority", "Thread.currentThread().setPriority(Thread.MIN_PRIORITY)"));
		
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

		generators.add(new IntMultiGenerator("PartialExpressionIndividualSize", 2, 1, 1) {
			@Override
			public void set() {
				SearchConfig.setPartialExpressionIndividualSize(this.getVal());
			}
		});
		
//	PartialExpressionBadMergePenalty : 1.6
		generators.add(new DoubleMultiGenerator("PartialExpressionBadMergePenalty", 1.3, 0.1, 4) {
			@Override
			public void set() {
				SearchConfig.setPartialExpressionBadMergePenalty(this.getVal());
			}
		});
			
//	PartialExpressionIndividualSizePenalty : 0.3
		generators.add(new DoubleMultiGenerator("PartialExpressionIndividualSizePenalty", 0.3, 0.1, 2) {
			@Override
			public void set() {
				SearchConfig.setPartialExpressionIndividualSizePenalty(this.getVal());
			}
		});

		generators.add(new DoubleMultiGenerator("PartialExpressionMergeReward", 0.0, 0.05, 0) {
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

		generators.add(new DoubleMultiGenerator("HoleWeight", 0.00, 0.005, 0) {
			@Override
			public void set() {
				// TODO Auto-generated method stub
				SearchConfig.setHoleWeight(this.getVal());
			}
		});

//	InputExprRepetitionPenalty : 0.9
		generators.add(new DoubleMultiGenerator("InputExprRepetitionPenalty", 0.9, 0.1, 4) {
			@Override
			public void set() {
				SearchConfig.setInputExprRepetitionPenalty(this.getVal());

			}
		});
	
//	CompositionWeightFactor : 0.6
		generators.add(new DoubleMultiGenerator("CompositionWeightFactor", 0.6, 0.1, 4) {
			@Override
			public void set() {
				SearchConfig.setCompositionWeightFactor(this.getVal());
			}
		});

//	InputExprWeight : 1.9
		generators.add(new DoubleMultiGenerator("InputExprWeight", 1.6, 0.1, 4) {
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

		ISTextTweakSynthesisNL tsnl = new ISTextTweakSynthesisNL(new ISText(config.getSnippetNumISText()), 
				config.getIterationNum(), config.<String, RankedValue>getStrategy());
		tsnl.tweak();
	}
}
