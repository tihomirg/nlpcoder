package results;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import api.Local;
import api.StabileAPI;
import results.table.Table;
import search.ISText;
import search.config.SearchConfig;
import types.StabileTypeFactory;
import types.Type;
import commons.examples.Example;
import commons.examples.SynthesisNLExample;
import commons.goals.PresentationTopSnippetGoal;
import commons.values.RankedValue;

public class TableGenerator {

	private List<ColumnGenerator> columnGens;
	private static final Local[] EMPTY_LOCALS = new Local[0];
	private List<Example<String, RankedValue>> examples;
	private ISText iSText;

	public TableGenerator(ISText iSText) {
		this.iSText = iSText;
		this.examples = getExamples(iSText);
		this.columnGens = getColumnGenerators(iSText);
	}

	private List<ColumnGenerator> getColumnGenerators(ISText iSText) {
		List<ColumnGenerator> gens = new LinkedList<ColumnGenerator>();

		gens.add(new ColumnGenerator("No", this.examples, 4, "c") {

			private int count = 1;
			
			@Override
			public void setSettings() {
			}

			@Override
			public String createCell(Example<String, RankedValue> example) {
				return Integer.toString(count++);
			}
		});
		
		gens.add(new ColumnGenerator("Input", this.examples, 60, "l") {

			@Override
			public void setSettings() {
			}

			@Override
			public String createCell(Example<String, RankedValue> example) {
				return example.getInput();
			}
		});

		gens.add(new ColumnGenerator("Output", this.examples, 70,"l") {

			@Override
			public void setSettings() {
			}

			@Override
			public String createCell(Example<String, RankedValue> example) {
				return example.getExprectedOuptup();
			}
		});
	
		gens.add(new ColumnGenerator("No Unigram-PCFG", this.examples, 10,"r") {

			@Override
			public void setSettings() {
				SearchConfig.setPartialExpressionBadMergePenalty(1.6);
				SearchConfig.setPartialExpressionMergeSizePenalty(0.5);
				SearchConfig.setPartialExpressionMergeReward(0.0);
				SearchConfig.setPartialExpressionIndividualSize(2);
				SearchConfig.setPartialExpressionIndividualSizePenalty(0.3);

				SearchConfig.setHoleWeight(0.00);
				SearchConfig.setInputExprRepetitionPenalty(0.9);
				
				//This is main change
				SearchConfig.setCompositionWeightFactor(0.0);

				double inputExprWeight = 1.9;
				SearchConfig.setLocalVariableWeight(inputExprWeight);
				SearchConfig.setNumberLiteralWeight(inputExprWeight);
				SearchConfig.setStringLiteralWeight(inputExprWeight);
				SearchConfig.setBooleanLiteralWeight(inputExprWeight);

				SearchConfig.setDeclarationInputUnmatchingWeight(0.0);
				SearchConfig.setPrimaryWeight(0.7);
				SearchConfig.setSecondaryWeight(0.3);
				SearchConfig.setRelatedWeigthFactor(0.9);
				//This is another change
				SearchConfig.setDeclarationScorerCoefs(new Double[]{1.0, 0.0});
				SearchConfig.setKindMatrix(new double[][]{{0.7, 0.3},{0.4, 0.5}});				
							
			}

			@Override
			public String createCell(Example<String, RankedValue> example) {
				return runAndFindRank(example);
			}
		});
		
		gens.add(new ColumnGenerator("No PCFG", this.examples, 10,"r") {

			@Override
			public void setSettings() {
				SearchConfig.setPartialExpressionBadMergePenalty(1.6);
				SearchConfig.setPartialExpressionMergeSizePenalty(0.5);
				SearchConfig.setPartialExpressionMergeReward(0.0);
				SearchConfig.setPartialExpressionIndividualSize(2);
				SearchConfig.setPartialExpressionIndividualSizePenalty(0.3);

				SearchConfig.setHoleWeight(0.00);
				SearchConfig.setInputExprRepetitionPenalty(0.9);
				
				//This is main change
				SearchConfig.setCompositionWeightFactor(0.0);

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
							
			}

			@Override
			public String createCell(Example<String, RankedValue> example) {
				return runAndFindRank(example);
			}
		});
		
		gens.add(new ColumnGenerator("Rank", this.examples, 10,"r") {

			@Override
			public void setSettings() {
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
							
			}

			@Override
			public String createCell(Example<String, RankedValue> example) {
				return runAndFindRank(example);
			}
		});

		gens.add(new ColumnGenerator("Time[ms]", this.examples, 10,"r") {

			@Override
			public void setSettings() {
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
							
			}

			@Override
			public String createCell(Example<String, RankedValue> example) {
				return getTime(example);
			}

		});		
		

		return gens;
	}

	public String getTime(Example<String, RankedValue> example) {
		long time = System.currentTimeMillis();
		example.run(this.iSText);
		return Long.toString(System.currentTimeMillis() - time);
	}
	
	public String runAndFindRank(Example<String, RankedValue> example) {
		example.run(this.iSText);
		return sugarRank(Integer.toString(example.getScore().getValue().getRank()));
	}

	private String sugarRank(String rank) {
		if (rank.equals("-1")){
			return "$>$10";
		} else return rank;
	}

	public Table create() {
		Table table = new Table(this.examples.size());
		for (ColumnGenerator columnGen : columnGens) {
			table.addColumn(columnGen.create());
		}
		return table;
	}

	protected List<Example<String, RankedValue>> getExamples(ISText iSText) {
		List<Example<String, RankedValue>> examples = new LinkedList<Example<String, RankedValue>>();
		StabileAPI api = iSText.getAPI();
		StabileTypeFactory typeFactory = api.getStf();

		Type stringType = typeFactory.createMonomorphicReferenceType(java.lang.String.class);

		//Paper examples
		examples.add(createSynthesisNLExample("copy file fname to destination", new Local[]{new Local("fname", stringType), new Local("destination", stringType)}, "FileUtils.copyFile(new File(fname), new File(destination))"));
		examples.add(createSynthesisNLExample("does x begin with y", new Local[]{new Local("x", stringType), new Local("y", stringType)}, "x.startsWith(y)"));
		examples.add(createSynthesisNLExample("load class \"MyClass.class\"", "Thread.currentThread().getContextClassLoader().loadClass(\"MyClass.class\")"));
		examples.add(createSynthesisNLExample("make file", "new File(<arg>).createNewFile()"));
		examples.add(createSynthesisNLExample("write \"hello\" to file \"text.txt\"", "FileUtils.writeStringToFile(new File(\"text.txt\"), \"hello\")"));
		examples.add(createSynthesisNLExample("readFile(\"text.txt\",\"UTF-8\")", "FileUtils.readFileToString(new File(\"text.txt\"), \"UTF-8\")"));

		//Other examples
		examples.add(createSynthesisNLExample("parse \"2015\"", "Integer.parseInt(\"2015\")"));
		examples.add(createSynthesisNLExample("substring \"OOPSLA2015\" 6", "\"OOPSLA2015\".substring(6)"));
		examples.add(createSynthesisNLExample("new buffered stream \"text.txt\"", "new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(\"text.txt\"))))"));
		examples.add(createSynthesisNLExample("get the current year", "new Date().getYear()"));
		examples.add(createSynthesisNLExample("current time", "System.currentTimeMillis()"));
		examples.add(createSynthesisNLExample("open connection \"http://www.oracle.com/\"", "new URL(\"http://www.oracle.com/\").openConnection()"));
		examples.add(createSynthesisNLExample("create socket \"http://www.oracle.com/\" 80", "new Socket(\"http://www.oracle.com/\", 80)"));
		examples.add(createSynthesisNLExample("put a pair (\"Mike\",\"+41-345-89-23\") into a map", "new HashMap().put(\"Mike\", \"+41-345-89-23\")"));
		examples.add(createSynthesisNLExample("set thread max priority", "Thread.currentThread().setPriority(Thread.MAX_PRIORITY)"));
		examples.add(createSynthesisNLExample("set property \"gate.home\" to value \"http://gate.ac.uk/\"", "new Properties().setProperty(\"gate.home\", \"http://gate.ac.uk/\")"));

		examples.add(createSynthesisNLExample("does the file \"text.txt\" exist", "new File(\"text.txt\").exists()"));
		examples.add(createSynthesisNLExample("min 1 3", "Math.min(1, 3)"));
		examples.add(createSynthesisNLExample("get thread id", "Thread.currentThread().getId()"));
		examples.add(createSynthesisNLExample("join threads", "Thread.currentThread().join()"));
		examples.add(createSynthesisNLExample("delete file \"text.txt\"", "new File(\"text.txt\").delete()"));
		examples.add(createSynthesisNLExample("print exception ex stack trace", 
				new Local[]{new Local("ex", typeFactory.createMonomorphicReferenceType(java.lang.Exception.class))},
				"ex.printStackTrace()"));
		examples.add(createSynthesisNLExample("is \"text.txt\" directory", "new File(\"text.txt\").isDirectory()"));
		examples.add(createSynthesisNLExample("get thread stack trace", "Thread.currentThread().getStackTrace()"));
		examples.add(createSynthesisNLExample("read line by line file \"text.txt\"", "FileUtils.readLines(new File(\"text.txt\"))"));
		examples.add(createSynthesisNLExample("set time zone to \"GMT\"", "Calendar.getInstance().setTimeZone(TimeZone.getTimeZone(\"GMT\"))"));
		
		examples.add(createSynthesisNLExample("pi", "Math.PI"));
		examples.add(createSynthesisNLExample("split \"OOPSLA-2015\" with \"-\"", "\"OOPSLA-2015\".split(\"-\")"));
		examples.add(createSynthesisNLExample("memory", "Runtime.getRuntime().freeMemory()"));
		examples.add(createSynthesisNLExample("free memory", "Runtime.getRuntime().freeMemory()"));
		examples.add(createSynthesisNLExample("total memory", "Runtime.getRuntime().totalMemory()"));
		examples.add(createSynthesisNLExample("exec \"javac.exe MyClass.java\"", "Runtime.getRuntime().exec(\"javac.exe MyClass.java\")"));
		examples.add(createSynthesisNLExample("new data stream \"text.txt\"", "new DataInputStream(new FileInputStream(\"text.txt\"))"));
		examples.add(createSynthesisNLExample("rename file \"text1.txt\" to \"text2.txt\"", "new File(\"text1.txt\").renameTo(new File(\"text2.txt\"))"));
		examples.add(createSynthesisNLExample("move file \"text1.txt\" to \"text2.txt\"", "FileUtils.moveFile(new File(\"text1.txt\"), new File(\"text2.txt\"))"));
		examples.add(createSynthesisNLExample("concat \"OOPSLA\" \"2015\"", "\"OOPSLA\".concat(\"2015\")"));

		examples.add(createSynthesisNLExample("read utf from the file \"text.txt\"", "new DataInputStream(new FileInputStream(\"text.txt\")).readUTF()"));
		examples.add(createSynthesisNLExample("java home", "SystemUtils.getJavaHome()"));		
		examples.add(createSynthesisNLExample("upper(text)", new Local[]{new Local("text", stringType)}, "text.toUpperCase()"));
		examples.add(createSynthesisNLExample("compare x y", new Local[]{new Local("x", stringType), new Local("y", stringType)},"x.compareTo(y)"));
		examples.add(createSynthesisNLExample("BufferedInput \"text.txt\"", "new BufferedInputStream(new FileInputStream(\"text.txt\"))"));
		examples.add(createSynthesisNLExample("set thread min priority", "Thread.currentThread().setPriority(Thread.MIN_PRIORITY)"));
		examples.add(createSynthesisNLExample("create panel and set layout to border", "new Panel().setLayout(new BorderLayout())"));
		examples.add(createSynthesisNLExample("sort array", 
				new Local[]{new Local("array", typeFactory.createArrayType(java.lang.Integer.class))},
				"Arrays.sort(array)"));
		examples.add(createSynthesisNLExample("add label \"Names:\" to panel", "new Panel().add(new Label(\"Names:\"))"));

		return examples;
	}

	private Example<String, RankedValue> createSynthesisNLExample(String input, String output) {
		return new SynthesisNLExample(input, new PresentationTopSnippetGoal(input, output));
	}

	private Example<String, RankedValue> createSynthesisNLExample(String input, Local[] locals, String output) {
		return new SynthesisNLExample(input, Arrays.asList(locals), new PresentationTopSnippetGoal(input, output));
	}

	public static void main(String[] args) {
		SearchConfig.setMaxSelectedDeclarations(10);
		TableGenerator generator = new TableGenerator(new ISText(10));
		Table table = generator.create();
		//System.out.println(table);
		table.print("C:/Users/gvero/papers/oopsla2015/table-training.tex");
	}
}
