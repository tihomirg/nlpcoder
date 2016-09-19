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

public class ExampleRunner {
	
	protected static final Local[] EMPTY_LOCALS = new Local[0];
	protected List<Example<String, RankedValue>> examples;
	protected Evaluator<String, RankedValue> eval;
	
	public ExampleRunner(ISText iSText) {
		this.examples = getExamples(iSText);
		this.eval = new Evaluator<String, RankedValue>(iSText, RankedValue.class, examples);
	}
	
	public void run() {
		List<List<String>> resultss = eval.run();
		for (int i=0; i < resultss.size(); i++) {
			List<String> results = resultss.get(i);
			Example<String, RankedValue> example = examples.get(i);
			
			System.out.println();
			System.out.println(example.getInput() + "   "+example.getScore());
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

		//Other examples
		examples.add(createSynthesisNLExample("parse \"t\"", "Integer.parseInt(\"t\")"));
		examples.add(createSynthesisNLExample("substring \"ICSE2015\" 4", "\"ICSE2015\".substring(4)"));
		examples.add(createSynthesisNLExample("new buffered stream \"t\"", "new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(\"t\"))))"));
		examples.add(createSynthesisNLExample("get a current year", "new Date().getYear()"));
		examples.add(createSynthesisNLExample("current time", "System.currentTimeMillis()"));
		examples.add(createSynthesisNLExample("open connection \"t\"", "new URL(\"t\").openConnection()"));
		examples.add(createSynthesisNLExample("create socket \"t\" 80", "new Socket(\"t\", 80)"));
		examples.add(createSynthesisNLExample("put a pair (\"Mike\",\"+41-345-89-23\") into a map", "new HashMap().put(\"Mike\", \"+41-345-89-23\")"));
		examples.add(createSynthesisNLExample("set thread max priority", "Thread.currentThread().setPriority(Thread.MAX_PRIORITY)"));
		examples.add(createSynthesisNLExample("set property \"gate.home\" to value \"http://gate.ac.uk/\"", "new Properties().setProperty(\"gate.home\", \"http://gate.ac.uk/\")"));

		examples.add(createSynthesisNLExample("does a file \"t\" exists", "new File(\"t\").exists()"));
		examples.add(createSynthesisNLExample("min 1 3", "Math.min(1, 3)"));
		examples.add(createSynthesisNLExample("get thread id", "Thread.currentThread().getId()"));
		examples.add(createSynthesisNLExample("join threads", "Thread.currentThread().join()"));
		examples.add(createSynthesisNLExample("delete file \"t\"", "new File(\"t\").delete()"));
		examples.add(createSynthesisNLExample("print exception ex stack trace", 
				new Local[]{new Local("ex", typeFactory.createMonomorphicReferenceType(java.lang.Exception.class))},
				"ex.printStackTrace()"));
		examples.add(createSynthesisNLExample("is file \"t\" directory", "new File(\"t\").isDirectory()"));
		examples.add(createSynthesisNLExample("get thread stack trace", "Thread.currentThread().getStackTrace()"));
		examples.add(createSynthesisNLExample("read line by line file \"text.txt\"", "FileUtils.readLines(new File(\"text.txt\"))"));
		examples.add(createSynthesisNLExample("set time zone to \"GMT\"", "Calendar.getInstance().setTimeZone(TimeZone.getTimeZone(\"GMT\"))"));
		
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

		examples.add(createSynthesisNLExample("read utf from a file \"text.txt\"", "new DataInputStream(new FileInputStream(\"text.txt\")).readUTF()"));
		examples.add(createSynthesisNLExample("java home", "SystemUtils.getJavaHome()"));		
		examples.add(createSynthesisNLExample("upper(\"t\")", "\"t\".toUpperCase()"));
		examples.add(createSynthesisNLExample("compare \"t1\" \"t2\"","\"t1\".compareToIgnoreCase(\"t2\")"));
		examples.add(createSynthesisNLExample("BufferedInput \"t\"", "new BufferedInputStream(new FileInputStream(\"t\"))"));
		examples.add(createSynthesisNLExample("set thread min priority", "Thread.currentThread().setPriority(Thread.MIN_PRIORITY)"));
		examples.add(createSynthesisNLExample("create panel and set layout to border", "new Panel().setLayout(new BorderLayout())"));
		examples.add(createSynthesisNLExample("sort array", 
				new Local[]{new Local("array", typeFactory.createArrayType(java.lang.Integer.class))},
				"Arrays.sort(array)"));
		examples.add(createSynthesisNLExample("add label \"names\" to panel", "new Panel().add(new Label(\"names\"))"));

		return examples;
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
		
		ExampleRunner runner = new ExampleRunner(new ISText(10));
		runner.run();
	}
}
