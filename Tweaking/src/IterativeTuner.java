import java.util.LinkedList;
import java.util.List;

import commons.scores.SingleScore;
import search.ISText;
import search.config.SearchConfig;


public class IterativeTuner {

	private int numOfIterations;
	private int upper;
	private int isTextSolutionNumber;
	private boolean print;
	private int randomIterations;

	public IterativeTuner(int randomIterations, int numOfIterations, int upper, int isTextSolutionNumber, boolean print) {
		this.numOfIterations = numOfIterations;
		this.upper = upper;
		this.isTextSolutionNumber = isTextSolutionNumber;
		this.print = print;
		this.randomIterations = randomIterations;
	}

	public void tweak() {
		//initiate ISText
		ISText isText = new ISText(this.isTextSolutionNumber);
		Evaluator eval = new Evaluator(isText, this.print);
		eval.addTests(createTests());

		//generator initialization
		IterativeGenerator generator = new IterativeGenerator();

		generator.addAll(getIndividualGenerators());

		//set a goal
		eval.setIndividualGoal(new Goal(this.upper));

		int generatorSize = generator.getSize();

		RandomReseter randomReseter = new RandomReseter(generator);

		for (int i = 0; i < this.randomIterations; i++) {
			
			SingleScore prev = null, curr = null;
			for (int j = 0; j < this.numOfIterations && !reachFixpoint(curr, prev); j++) {
				for (int k = 0; k < generatorSize; k++) {
					//Set SearchConfig values
					generator.set();

					//Run tests
					SingleScore score = eval.run();

					print(score);
					//Use score to update generators
					generator.updateScore(score);

					//print(generator.getCurr());
					generator.next();
					//print(generator.getCurr());
				}

				prev = curr;
				curr = generator.getBestScore();
				
				System.out.println(generator);
			}

			randomReseter.reset();
		}
		
		System.out.println(randomReseter.getBestScore());
	}

	private boolean reachFixpoint(SingleScore curr, SingleScore previous) {
		if(curr == null || previous == null) return false;
		return curr.getValue() == previous.getValue();
	}

	private void print(ParameterGenerator curr) {
		System.out.println(curr);
	}

	private void print(SingleScore score) {
		if(this.print) System.out.println(score);
	}

	/*

	//Synthesis weights
	private static double partialExpressionBadMergePenalty = 1.0;
	private static double partialExpressionMergeSizePenalty = 0.4;
	private static double partialExpressionMergeReward = 0.2;

	//Individual (partial) expression
	private static double partialExpressionIndividualSizePenalty = 0.4;
	private static int partialExpressionIndividualSize = 2; 

	//Compositions
	private static double compositionWeightFactor = 0.4;
	private static double holeWeight = 0.2;

	//Input Exprs
	private static double inputExprRepetitionPenalty = 1.0;
	private static double localVariableWeight = 0.6;
	private static double numberLiteralWeight = 0.6;
	private static double stringLiteralWeight = 0.6;
	private static double booleanLiteralWeight = 0.6;
	 */

	/*
	 * 	//Declaration Selection weights
	    private static double[][] kindMatrix = new double[][]{{1.0, 0.75},{0.25, 0.75}};
	    private static double declarationInputUnmatchingWeight = 0.02;
	    private static Double[] declarationScorerCoefs = new Double[]{0.80, 0.20};
	 * 
	 */
	
	private List<ParameterGenerator> getIndividualGenerators() {
		List<ParameterGenerator> generators = new LinkedList<ParameterGenerator>();

		generators.add(new DoubleGenerator("kindMatrix[0][1]", 0.70, 0.2, 1) {
			@Override
			public void set() {
				double[][] kindMatrix = SearchConfig.getDeclarationInputKindMatrix();
				kindMatrix[0][1] = this.getVal();
			}
		});

		generators.add(new DoubleGenerator("kindMatrix[1][0]", 0.30, 0.2, 1) {
			@Override
			public void set() {
				double[][] kindMatrix = SearchConfig.getDeclarationInputKindMatrix();
				kindMatrix[1][0] = this.getVal();				
			}
		});
		
		generators.add(new DoubleGenerator("kindMatrix[1][1]", 0.70, 0.2, 1) {
			@Override
			public void set() {
				double[][] kindMatrix = SearchConfig.getDeclarationInputKindMatrix();
				kindMatrix[1][1] = this.getVal();				
			}
		});		

		generators.add(new DoubleGenerator("declarationInputUnmatchingWeight", 0.04, 0.02, 1) {
			@Override
			public void set() {
				SearchConfig.setDeclarationInputUnmatchingWeight(this.getVal());
			}
		});

		generators.add(new DoubleGenerator("declarationScorerCoefs", 0.4, 0.2, 1) {
			@Override
			public void set() {
				double val = this.getVal();
				Double[] coefs = SearchConfig.getDeclarationScorerCoefs();
				coefs[0] = 1.0 - val;
				coefs[1] = val;
			}
		});
		
		generators.add(new IntGenerator("PartialExpressionIndividualSize", 2, 1, 1) {
			@Override
			public void set() {
				SearchConfig.setPartialExpressionIndividualSize(this.getVal());
			}
		});

		generators.add(new DoubleGenerator("PartialExpressionBadMergePenalty", 0.9, 0.2, 1) {
			@Override
			public void set() {
				SearchConfig.setPartialExpressionBadMergePenalty(this.getVal());
			}
		});

		generators.add(new DoubleGenerator("PartialExpressionIndividualSizePenalty", 0.1, 0.05, 1) {
			@Override
			public void set() {
				SearchConfig.setPartialExpressionIndividualSizePenalty(this.getVal());
			}
		});

		generators.add(new DoubleGenerator("PartialExpressionMergeReward", 0.2, 0.2, 1) {
			@Override
			public void set() {
				SearchConfig.setPartialExpressionMergeReward(this.getVal());
			}
		});

		generators.add(new DoubleGenerator("PartialExpressionMergeSizePenalty", 0.6, 0.2, 1) {
			@Override
			public void set() {
				SearchConfig.setPartialExpressionMergeSizePenalty(this.getVal());
			}
		});

		generators.add(new DoubleGenerator("HoleWeight", 0.2, 0.2, 1) {
			@Override
			public void set() {
				// TODO Auto-generated method stub
				SearchConfig.setHoleWeight(this.getVal());
			}
		});

		generators.add(new DoubleGenerator("InputExprRepetitionPenalty", 0.9, 0.2, 1) {
			@Override
			public void set() {
				SearchConfig.setInputExprRepetitionPenalty(this.getVal());

			}
		});

		generators.add(new DoubleGenerator("InputExprWeight", 0.8, 0.2, 1) {
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

	private List<Example> createTests() {
		List<Example> tests = new LinkedList<Example>();

		//1-10
		tests.add(new Example("copy file \"t1\" to \"t2\"", "FileUtils.copyFileToDirectory(new File(\"t1\"), new File(\"t2\"))"));
		tests.add(new Example("open file \"t\"", "FileUtils.openOutputStream(new File(\"t\"))"));
		tests.add(new Example("read file \"t\"", "FileUtils.readFileToString(new File(\"t\"))"));
		tests.add(new Example("parse \"t\"", "Integer.parseInt(\"t\")"));
		tests.add(new Example("substring \"t\" 1", "\"t\".substring(1)"));
		tests.add(new Example("new buffered stream \"t\"", "new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(\"t\"))))"));
		tests.add(new Example("get a current year from a calendar", "new GregorianCalendar().get(Calendar.YEAR)"));
		tests.add(new Example("leap year", "new GregorianCalendar().isLeapYear(Calendar.getInstance().get(Calendar.YEAR))"));
		tests.add(new Example("current time", "System.currentTimeMillis()"));
		tests.add(new Example("open connection \"t\"", "new URL(\"t\").openConnection()"));

		//11-20
		tests.add(new Example("new socket \"t\" 80", "new socket \"t\" 80"));
		tests.add(new Example("put \"t1\" \"t2\" pair into a map", "new HashMap().put(\"t1\", \"t2\")"));
		tests.add(new Example("set thread max priority", "Thread.currentThread().setPriority(Thread.MAX_PRIORITY)"));
		tests.add(new Example("get property \"t\"", "new Properties().getProperty(\"t\")"));
		tests.add(new Example("does a file \"t\" exists", "new File(\"t\").exists()"));
		tests.add(new Example("min 1 3", "Math.min(1, 3)"));
		tests.add(new Example("get thread id", "Thread.currentThread().getId()"));
		tests.add(new Example("join threads", "Thread.currentThread().join()"));
		tests.add(new Example("delete file", "new File(\"t\").delete()"));
		tests.add(new Example("print exception stack trace", "new Exception(\"t\").printStackTrace(System.out)"));

		//21-30
		tests.add(new Example("is file \"t\" directory", "new File(\"t\").isDirectory()"));
		tests.add(new Example("get thread stack trace", "Thread.currentThread().getStackTrace()"));
		tests.add(new Example("read file \"t\", line by line", "new LineNumberReader(new FileReader(\"t\")).readLine()"));
		tests.add(new Example("write \"t1\" to a file \"t2\"", "new FileWriter(new File(\"t2\")).write(\"t1\")"));
		tests.add(new Example("set time zone to \"t\"", "Calendar.getInstance().setTimeZone(TimeZone.getTimeZone(\"t\"))"));
		tests.add(new Example("empty map", "Collections.emptyMap()"));
		tests.add(new Example("pi", "Math.PI"));
		tests.add(new Example("load class \"t\"", "Thread.currentThread().getContextClassLoader().loadClass(\"t\")"));
		tests.add(new Example("split \"t1\" with \"t2\"", "\"t1\".split(\"t2\")"));
		tests.add(new Example("memory", "Runtime.getRuntime().freeMemory()"));

		//31-40
		tests.add(new Example("free memory", "Runtime.getRuntime().freeMemory()"));
		tests.add(new Example("total memory", "Runtime.getRuntime().totalMemory()"));
		tests.add(new Example("exec \"t\"", "Runtime.getRuntime().exec(\"t\")"));
		tests.add(new Example("new data stream \"t\"", "new DataInputStream(new FileInputStream(\"t\"))"));
		tests.add(new Example("rename file \"t1\" to \"t2\"", "new File(\"t1\").renameTo(new File(\"t2\"))"));
		tests.add(new Example("move file \"t1\" to \"t2\"", "FileUtils.moveFile(new File(\"t1\"), new File(\"t2\"))"));
		tests.add(new Example("write a string \"t1\" to a file \"t2\"", "new FileWriter(new File(\"t2\")).write(\"t1\")"));
		tests.add(new Example("concat \"t1\" \"t2\"", "\"t1\".concat(\"t2\")"));
		tests.add(new Example("write a utf \"t1\" to a file \"t2\"", "new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(\"t2\")))).writeUTF(\"t1\")"));
		tests.add(new Example("java home", "SystemUtils.getJavaHome()"));		

		//41-45
		tests.add(new Example("\"t\" lower case", "t.toLowerCase()"));
		tests.add(new Example("upper (\"t\")", "\"t\".toUpperCase()"));
		tests.add(new Example("compare \"t1\" \"t2\"","\"t1\".compareToIgnoreCase(\"t2\")"));
		tests.add(new Example("BufferedInput \"t\"", "new BufferedInputStream(new FileInputStream(\"t\"))"));
		tests.add(new Example("set thread min priority", "Thread.currentThread().setPriority(Thread.MIN_PRIORITY)"));
		return tests;
	}

	public static void main(String[] args) {
		int numOfSolutions = 5;
		IterativeTuner tweaker = new IterativeTuner(2, 2, numOfSolutions-1, numOfSolutions, true);

		tweaker.tweak();
	}

}
