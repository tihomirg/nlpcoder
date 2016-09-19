package results;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

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

public class AdditionalTableGenerator {

	private List<ColumnGenerator> columnGens;
	private static final Local[] EMPTY_LOCALS = new Local[0];
	private List<Example<String, RankedValue>> examples;
	private ISText iSText;

	public AdditionalTableGenerator(ISText iSText) {
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
		Type keyStrokeType = typeFactory.createMonomorphicReferenceType(javax.swing.KeyStroke.class);
		Type objType = typeFactory.createMonomorphicReferenceType(java.lang.Object.class);
		Type doubleType = typeFactory.createPrimitiveType("double");
		Type intArray = typeFactory.createArrayType("int");
		Type byteArray = typeFactory.createArrayType("byte");
		Type vectorType = typeFactory.createPolymorphicType(java.util.Vector.class, new Type[]{stringType});
		Type linkedListType = typeFactory.createPolymorphicType(java.util.LinkedList.class, new Type[]{stringType});
		Type propertiesType = typeFactory.createMonomorphicReferenceType(java.util.Properties.class);
		Type labelType = typeFactory.createMonomorphicReferenceType(javax.swing.JLabel.class);
		Type robotType = typeFactory.createMonomorphicReferenceType(java.awt.Robot.class);
		Type mapType = typeFactory.createMonomorphicReferenceType(java.util.Map.class);
		Type fileType = typeFactory.createMonomorphicReferenceType(java.io.File.class);
		
		//Other examples
		examples.add(createSynthesisNLExample("write 2015 to data ouput stream \"text.txt\"","new DataOutputStream(new FileOutputStream(\"text.txt\")).write(2015)"));
		examples.add(createSynthesisNLExample("get date when file \"text.txt\" was last time modified","new Date(new File(\"text.txt\").lastModified()).getTime()"));
		examples.add(createSynthesisNLExample("check file \"text.txt\" \"read\" permission","AccessController.checkPermission(new FilePermission(\"text.txt\", \"read\"))"));
		examples.add(createSynthesisNLExample("read lines with numbers from file \"text.txt\"","new LineNumberReader(new InputStreamReader(new FileInputStream(\"text.txt\"))).readLine()"));
		examples.add(createSynthesisNLExample("StreamTokenizer(\"text.txt\")","new StreamTokenizer(new BufferedReader(new FileReader(\"text.txt\")))"));
		examples.add(createSynthesisNLExample("read from console","new BufferedReader(new InputStreamReader(System.in)).readLine()"));
		examples.add(createSynthesisNLExample("is file \"text.txt\" data available","new DataInputStream(new FileInputStream(\"text.txt\")).available()"));
		examples.add(createSynthesisNLExample("SequenceInputStream(\"text1.txt\", \"text2.txt\")","new SequenceInputStream(new FileInputStream(\"text1.txt\"), new FileInputStream(\"text2.txt\"))"));
		examples.add(createSynthesisNLExample("get double value x", new Local[]{new Local("x", doubleType)},"Double.valueOf(x).doubleValue()"));
		examples.add(createSynthesisNLExample("write object o to file output stream \"data.obj\"", new Local[]{new Local("o", objType)},"new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(\"data.obj\"))).writeObject(o)"));
		examples.add(createSynthesisNLExample("1 xor 5","new BitSet(1).xor(new BitSet(5))"));
		examples.add(createSynthesisNLExample("create bit set and set its 5th element to true","new BitSet(5)"));
		examples.add(createSynthesisNLExample("accept request on port 80","new ServerSocket(80).accept()"));
		examples.add(createSynthesisNLExample("ResourceStream(\"text.txt\")","ClassLoader.getSystemResourceAsStream(\"text.txt\")"));
		examples.add(createSynthesisNLExample("gaussian","new Random(System.currentTimeMillis()).nextGaussian()"));
		
		//
		examples.add(createSynthesisNLExample("thread group","Thread.currentThread().getThreadGroup()"));
		examples.add(createSynthesisNLExample("create panel and set layout to grid","new Panel().setLayout(new GridBagLayout())"));
		examples.add(createSynthesisNLExample("get screen size","Toolkit.getDefaultToolkit().getScreenSize()"));
		examples.add(createSynthesisNLExample("get splash screen graphics","SplashScreen.getSplashScreen().createGraphics()"));
		examples.add(createSynthesisNLExample("get v's 10th element", new Local[]{new Local("v", vectorType)},  "v.elementAt(10)"));
		examples.add(createSynthesisNLExample("polygon x y", new Local[]{new Local("x", intArray), new Local("y", intArray)},  "new Polygon(x, y, x.length)"));
		examples.add(createSynthesisNLExample("dialog \"Welcome!\"","JOptionPane.showMessageDialog(null, \"Welcome!\")"));
		examples.add(createSynthesisNLExample("get display refresh rate","GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getRefreshRate()"));
		examples.add(createSynthesisNLExample("make obj's string using reflection", new Local[]{new Local("obj", objType)}, "ToStringBuilder.reflectionToString(obj)"));
		examples.add(createSynthesisNLExample("get obj's hash code using reflection", new Local[]{new Local("obj", objType)}, "HashCodeBuilder.reflectionHashCode(obj)"));
		
		examples.add(createSynthesisNLExample("are x and y equal with respect to reflection", new Local[]{new Local("x", objType), new Local("y", objType)},"EqualsBuilder.reflectionEquals(x, y)"));
		examples.add(createSynthesisNLExample("get keystroke modifiers", new Local[]{new Local("keystroke", keyStrokeType)}, "KeyEvent.getKeyModifiersText(keystroke.getModifiers())"));
		examples.add(createSynthesisNLExample("generate \"RSA\" private key","KeyPairGenerator.getInstance(\"RSA\").generateKeyPair().getPrivate()"));
		examples.add(createSynthesisNLExample("get \"MyClass.class\" source code","Class.forName(\"MyClass.class\").getProtectionDomain().getCodeSource()"));
		examples.add(createSynthesisNLExample("new x instance", new Local[]{new Local("x", objType)}, "Class.forName(x).newInstance()"));
		examples.add(createSynthesisNLExample("add mouse press to robot", new Local[]{new Local("robot", robotType)}, "robot.mousePress(InputEvent.BUTTON1_MASK)"));
		examples.add(createSynthesisNLExample("reverse list", new Local[]{new Local("list", linkedListType)}, "Collections.reverse(list)"));
		examples.add(createSynthesisNLExample("convert prop", new Local[]{new Local("prop", propertiesType)}, "ExtendedProperties.convertProperties(prop)"));
		examples.add(createSynthesisNLExample("intersection of rectangle 4 5 with rectangle 3 2", "new Rectangle(5, 4).intersection(new Rectangle(3, 2))"));
		examples.add(createSynthesisNLExample("set cursor over label to hand", new Local[]{new Local("label", labelType)}, "label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR))"));
		
		examples.add(createSynthesisNLExample("read big integer from console","new Scanner(System.in).nextBigInteger()"));
		examples.add(createSynthesisNLExample("delete file \"text.txt\" when JVM terminates","new File(\"text.txt\").deleteOnExit()"));
		examples.add(createSynthesisNLExample("if blank(x) \"2015\" else x", new Local[]{new Local("x", stringType)},"StringUtils.defaultIfBlank(x, \"2015\")"));
		examples.add(createSynthesisNLExample("get date instance for Germany", "DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.GERMANY)"));
		examples.add(createSynthesisNLExample("processors", "Runtime.getRuntime().availableProcessors()"));
		examples.add(createSynthesisNLExample("set command \"enable\" to formatted text field x", new Local[]{new Local("x", stringType)}, "new JFormattedTextField(x).setActionCommand(\"enable\")"));
		examples.add(createSynthesisNLExample("does map include value 1", new Local[]{new Local("map", mapType)}, "map.containsValue(1)"));
		examples.add(createSynthesisNLExample("move x to y", new Local[]{new Local("x", fileType), new Local("y", fileType)}, "FileUtils.moveFile(x, y)"));
		examples.add(createSynthesisNLExample("writeBytes(bytes, fname)", new Local[]{new Local("fname", stringType), new Local("bytes", byteArray)}, "FileUtils.writeByteArrayToFile(new File(fname), bytes)"));
		
		//do not work
		examples.add(createSynthesisNLExample("new horizontal slider 0 50 25", "new JSlider(JSlider.HORIZONTAL, 0, 50, 25)"));
		
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
		AdditionalTableGenerator generator = new AdditionalTableGenerator(new ISText(1000));
		Table table = generator.create();
		System.out.println(table);
		//table.print("C:/Users/gvero/papers/oopsla2015/table-results.tex");
	}
}
