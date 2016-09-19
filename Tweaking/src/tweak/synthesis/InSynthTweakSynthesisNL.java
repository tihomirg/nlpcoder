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

public class InSynthTweakSynthesisNL extends ISTextParameterGeneratorAndExampleRunner<String, RankedValue> {

	private int maxNumberOfSteps;

	public InSynthTweakSynthesisNL(ISText iSText, int maxNumberOfSteps, ParameterStrategy<String, RankedValue> paramStrategy) {
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
		
		examples.add(createSynthesisNLExample(java.awt.AWTPermission.class,"\"t\" args", new Local[]{args},
				"new AWTPermission(\"t\")"));

		examples.add(createSynthesisNLExample(java.io.BufferedInputStream.class,"fis args", new Local[]{args, 
			new Local("fis", fisType)}, 
			"new BufferedInputStream(fis)"));

		examples.add(createSynthesisNLExample(java.io.BufferedOutputStream.class,"file numPrimes primes args", new Local[]{args, 
			new Local("numPrimes", intType),
			new Local("primes", intArrayType),
			new Local("file", fosType)},
			"new BufferedOutputStream(file)"));

		examples.add(createSynthesisNLExample(java.io.BufferedReader.class,"fr args", new Local[]{args,
			new Local("fr", fileReaderType)},
			"new BufferedReader(fr)"));

		examples.add(createSynthesisNLExample(java.io.BufferedReader.class,"isr args", new Local[]{args,
			new Local("isr", typeFactory.createMonomorphicReferenceType(java.io.InputStreamReader.class))},
			"new BufferedReader(isr)"));

		examples.add(createSynthesisNLExample(java.io.BufferedReader.class,"url args", 
				new Local[]{args,
			new Local("url", typeFactory.createMonomorphicReferenceType(java.net.URL.class))},
			"new BufferedReader(new InputStreamReader(url.openStream()))"));

		examples.add(createSynthesisNLExample(java.io.ByteArrayInputStream.class,"input1 b tmp args", new Local[]{args,
			new Local("input1", typeFactory.createMonomorphicReferenceType(java.io.ByteArrayInputStream.class)),
			new Local("b", typeFactory.createArrayType("byte")),
			new Local("tmp", stringType)},
			"new ByteArrayInputStream(b, 0, 0)"));

		examples.add(createSynthesisNLExample(java.io.ByteArrayOutputStream.class,"12 args", new Local[]{args},
				"new ByteArrayOutputStream(12)"));

		examples.add(createSynthesisNLExample(java.net.DatagramSocket.class,"ia args", new Local[]{args,
			new Local("ia", typeFactory.createMonomorphicReferenceType(java.net.InetAddress.class))},
			"new DatagramSocket()"));

		examples.add(createSynthesisNLExample(java.io.DataInputStream.class,"fis args", new Local[]{args,
			new Local("fis", fisType)},
			"new DataInputStream(fis)"));

		examples.add(createSynthesisNLExample(java.io.DataOutputStream.class,"fos args", new Local[]{args,
			new Local("fos", fosType)},
			"new DataOutputStream(fos)"));

		examples.add(createSynthesisNLExample(javax.swing.DefaultBoundedRangeModel.class,"args", new Local[]{args},
				"new DefaultBoundedRangeModel()"));

		examples.add(createSynthesisNLExample(java.awt.DisplayMode.class,"canChg gs ge args", new Local[]{args,
			new Local("ge", typeFactory.createMonomorphicReferenceType(java.awt.GraphicsEnvironment.class)),
			new Local("canChg", boolType),
			new Local("gs", typeFactory.createMonomorphicReferenceType(java.awt.GraphicsDevice.class))},
			"gs.getDisplayMode()"));

		examples.add(createSynthesisNLExample(java.io.FileInputStream.class,"fd inputFile1 aFile args", new Local[]{args,
			new Local("fd", typeFactory.createMonomorphicReferenceType(java.io.FileDescriptor.class)),
			new Local("inputFile1", fisType),
			new Local("aFile", fileType)},
			"new FileInputStream(aFile)"));

		examples.add(createSynthesisNLExample(java.io.FileInputStream.class,"\"fileName.dat\" args", new Local[]{args},
				"new FileInputStream(\"fileName.dat\")"));

		examples.add(createSynthesisNLExample(java.io.FileOutputStream.class,"tempFile args", new Local[]{args,
			new Local("tempFile", fileType)},
			"new FileOutputStream(tempFile)"));

		examples.add(createSynthesisNLExample(java.io.FileReader.class,"outputFile inputFile args", new Local[]{args,
			new Local("inputFile", fileType),
			new Local("outputFile", fileType)},
			"new FileReader(inputFile)"));

		examples.add(createSynthesisNLExample(java.io.File.class,"\"MainClass.java\" args", new Local[]{args},
				"new File(\"MainClass.java\")"));

		examples.add(createSynthesisNLExample(java.io.FileWriter.class,"in outputFile inputFile args", new Local[]{args,
			new Local("inputFile", fileType),
			new Local("in", fileReaderType),
			new Local("outputFile", fileType)},
			"new FileWriter(outputFile)"));

		examples.add(createSynthesisNLExample(java.io.FileWriter.class,"\"LPT1:\" args", new Local[]{args},
				"new FileWriter(\"LPT1:\")"));

		examples.add(createSynthesisNLExample(java.awt.GridBagConstraints.class,"gridbag", new Local[]{
			new Local("gridbag", typeFactory.createMonomorphicReferenceType(java.awt.GridBagLayout.class))},
			"new GridBagConstraints()"));

		examples.add(createSynthesisNLExample(java.awt.GridBagLayout.class, "", new Local[]{},
				"new GridBagLayout()"));

		examples.add(createSynthesisNLExample(javax.swing.GroupLayout.class,"panel frame args", new Local[]{args,
			new Local("frame", frameType),
			new Local("panel", panelType)},
			"new GroupLayout(panel)"));

		examples.add(createSynthesisNLExample(javax.swing.ImageIcon.class,"\"yourFile.gif\" args", new Local[]{args},
				"new ImageIcon(\"yourFile.gif\")"));

		examples.add(createSynthesisNLExample(java.io.InputStreamReader.class,"args", new Local[]{args},
				"new InputStreamReader(System.in)"));

		examples.add(createSynthesisNLExample(javax.swing.JButton.class,"\"Button1\"", new Local[]{},
				"new JButton(\"Button1\")"));

		examples.add(createSynthesisNLExample(javax.swing.JCheckBox.class,"\"Anchovies\" border panel frame title args", new Local[]{args,
			new Local("title", stringType),
			new Local("panel", panelType),
			new Local("frame", frameType),
			new Local("border", typeFactory.createMonomorphicReferenceType(javax.swing.border.Border.class))},
			"new JCheckBox(\"Anchovies\")"));

		examples.add(createSynthesisNLExample(javax.swing.JFormattedTextField.class,"factory editFormatter editFormat displayFormatter displayFormat frame args", new Local[]{args,
			new Local("frame", frameType),
			new Local("displayFormat", dateFormatType),
			new Local("displayFormatter", dateFormatterType),
			new Local("editFormat", dateFormatType),
			new Local("editFormatter", dateFormatterType),
			new Local("factory", typeFactory.createMonomorphicReferenceType(javax.swing.text.DefaultFormatterFactory.class))},
			"new JFormattedTextField(factory, new Date())"));

		examples.add(createSynthesisNLExample(javax.swing.text.MaskFormatter.class,"rowOne f args", new Local[]{args,
			new Local("f", frameType),
			new Local("rowOne", typeFactory.createMonomorphicReferenceType(javax.swing.Box.class))},
			"new MaskFormatter(\"###-##-####\")"));

		examples.add(createSynthesisNLExample(javax.swing.JTable.class,"columns rows f args", new Local[]{args,
			new Local("f", frameType),
			new Local("rows", matrixObjectType),			
			new Local("columns", arrayObjType)},
			"new JTable(rows, columns)"));

		examples.add(createSynthesisNLExample(javax.swing.JTextArea.class,"\"Initial Text\" args", new Local[]{args},
				"new JTextArea(\"Initial Text\")"));

		examples.add(createSynthesisNLExample(javax.swing.JFrame.class,"\"JToggleButton Sample\" args", new Local[]{args},
				"new JFrame(\"JToggleButton Sample\")"));

		examples.add(createSynthesisNLExample(javax.swing.JTree.class,"f args", new Local[]{args,
			new Local("f", frameType)},
			"new JTree()"));

		examples.add(createSynthesisNLExample(javax.swing.JViewport.class,"scrollPane table frame fname headers rows args", new Local[]{args,
			new Local("frame", frameType),
			new Local("fname", stringType),
			new Local("rows", matrixObjectType),			
			new Local("headers", arrayObjType),
			new Local("table", typeFactory.createMonomorphicReferenceType(javax.swing.JTable.class)),
			new Local("scrollPane", typeFactory.createMonomorphicReferenceType(javax.swing.JScrollPane.class))},
			"new JViewport()"));

		examples.add(createSynthesisNLExample(javax.swing.JWindow.class,"args", new Local[]{args},
				"new JWindow()"));

		examples.add(createSynthesisNLExample(java.io.LineNumberReader.class,"args", new Local[]{args},
				"new LineNumberReader(new InputStreamReader(System.in))"));

		examples.add(createSynthesisNLExample(java.io.ObjectInputStream.class,"fis oos fos list args", new Local[]{args,
			new Local("list", typeFactory.createPolymorphicType(java.util.List.class, new Type[]{stringType})),
			new Local("fos", fosType),
			new Local("fis", fisType),
			new Local("oos", typeFactory.createMonomorphicReferenceType(java.io.ObjectOutputStream.class))},
			"new ObjectInputStream(fis)"));

		examples.add(createSynthesisNLExample(java.io.ObjectOutputStream.class,"fos list args", new Local[]{args,
			new Local("list", typeFactory.createPolymorphicType(java.util.List.class, new Type[]{stringType})),
			new Local("fos", fosType)},
			"new ObjectOutputStream(fos)"));

		examples.add(createSynthesisNLExample(java.io.PipedReader.class,"pw args", new Local[]{args,
			new Local("pw", typeFactory.createMonomorphicReferenceType(java.io.PipedWriter.class))},
			"new PipedReader(pw)"));

		examples.add(createSynthesisNLExample(java.io.PipedWriter.class,"args", new Local[]{args},
				"new PipedWriter()"));

		examples.add(createSynthesisNLExample(java.awt.Point.class,"50 25 aPoint args", new Local[]{args,
			new Local("aPoint", typeFactory.createMonomorphicReferenceType(java.awt.Point.class))},
			"new Point(50, 25)"));

		examples.add(createSynthesisNLExample(java.io.PrintStream.class,"fout tempFile args", new Local[]{args,
			new Local("tempFile", fileType),
			new Local("fout", fosType)},
			"new PrintStream(fout)"));

		examples.add(createSynthesisNLExample(java.io.PrintWriter.class,"false bw fw args", new Local[]{args,
			new Local("fw", typeFactory.createMonomorphicReferenceType(java.io.FileWriter.class)),
			new Local("bw", typeFactory.createMonomorphicReferenceType(java.io.BufferedWriter.class))},
			"new PrintWriter(bw, false)"));

		examples.add(createSynthesisNLExample(java.io.SequenceInputStream.class,"false ftwo fone args", new Local[]{args,
			new Local("fone", fisType),
			new Local("ftwo", fisType)},
			"new SequenceInputStream(fone, ftwo)"));

		examples.add(createSynthesisNLExample(java.net.ServerSocket.class,"port args", new Local[]{args,
			new Local("port", intType)},
			"new ServerSocket(port)"));

		examples.add(createSynthesisNLExample(java.io.StreamTokenizer.class,"br fr args", new Local[]{args,
			new Local("fr", fileReaderType),
			new Local("br", typeFactory.createMonomorphicReferenceType(java.io.BufferedReader.class))},
			"new StreamTokenizer(br)"));

		examples.add(createSynthesisNLExample(java.io.StringReader.class,"\"a bc ddd\" args", new Local[]{args},
				"new StringReader(\"a bc ddd\")"));

		examples.add(createSynthesisNLExample(javax.swing.Timer.class,"500 actionListener args", new Local[]{args,
			new Local("actionListener", typeFactory.createMonomorphicReferenceType(java.awt.event.ActionListener.class))},
			"new Timer(500, actionListener)"));

		examples.add(createSynthesisNLExample(javax.swing.TransferHandler.class,"s args", new Local[]{
			new Local("s", stringType)},
			"new TransferHandler(s)"));

		examples.add(createSynthesisNLExample(java.net.URLConnection.class,"\"http://www.yourserver.com\" args", new Local[]{args},
			"new URL(\"http://www.yourserver.com\").openConnection()"));

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

		generators.add(new IntMultiGenerator("PartialExpressionIndividualSize", 3, 1, 0) {
			@Override
			public void set() {
				SearchConfig.setPartialExpressionIndividualSize(this.getVal());
			}
		});

		generators.add(new DoubleMultiGenerator("PartialExpressionBadMergePenalty", 1.1, 0.025, 6) {
			@Override
			public void set() {
				SearchConfig.setPartialExpressionBadMergePenalty(this.getVal());
			}
		});

		generators.add(new DoubleMultiGenerator("PartialExpressionIndividualSizePenalty", 0.75, 0.025, 6) {
			@Override
			public void set() {
				SearchConfig.setPartialExpressionIndividualSizePenalty(this.getVal());
			}
		});

		generators.add(new DoubleMultiGenerator("PartialExpressionMergeReward", 0.1, 0.025, 2) {
			@Override
			public void set() {
				SearchConfig.setPartialExpressionMergeReward(this.getVal());
			}
		});

		generators.add(new DoubleMultiGenerator("PartialExpressionMergeSizePenalty", 0.5, 0.025, 6) {
			@Override
			public void set() {
				SearchConfig.setPartialExpressionMergeSizePenalty(this.getVal());
			}
		});

		generators.add(new DoubleMultiGenerator("HoleWeight", 0.05, 0.025, 1) {
			@Override
			public void set() {
				// TODO Auto-generated method stub
				SearchConfig.setHoleWeight(this.getVal());
			}
		});

		generators.add(new DoubleMultiGenerator("InputExprRepetitionPenalty", 1.0, 0.025, 6) {
			@Override
			public void set() {
				SearchConfig.setInputExprRepetitionPenalty(this.getVal());

			}
		});

		generators.add(new DoubleMultiGenerator("CompositionWeightFactor", 0.475, 0.025, 6) {
			@Override
			public void set() {
				SearchConfig.setCompositionWeightFactor(this.getVal());
			}
		});

		generators.add(new DoubleMultiGenerator("InputExprWeight", 1.05, 0.025, 6) {
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

		SearchConfig.setDeclarationInputUnmatchingWeight(0.01);
		SearchConfig.setPrimaryWeight(0.6);
		SearchConfig.setSecondaryWeight(0.4);
		SearchConfig.setRelatedWeigthFactor(0.5);
		SearchConfig.setDeclarationScorerCoefs(new Double[]{0.8, 0.2});
		SearchConfig.setKindMatrix(new double[][]{{0.5, 0.2},{0.2, 0.5}});

		InSynthTweakSynthesisNL tsnl = new InSynthTweakSynthesisNL(new ISText(config.getSnippetNumISText()), 
				config.getIterationNum(), config.<String, RankedValue>getStrategy());
		tsnl.tweak();
	}
}
