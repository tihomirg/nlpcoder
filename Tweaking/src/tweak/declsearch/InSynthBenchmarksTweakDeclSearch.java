package tweak.declsearch;

import java.awt.AWTPermission;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.lang.model.type.NoType;

import api.Local;
import api.StabileAPI;
import commons.ISTextParameterGeneratorAndExampleRunner;
import commons.examples.DeclSearchExample;
import commons.examples.Example;
import commons.examples.SynthesisNLExample;
import commons.goals.Goal;
import commons.goals.TopDeclGoal;
import commons.goals.TopSnippetGoal;
import commons.parameters.DoubleMultiGenerator;
import commons.parameters.ParameterGenerator;
import commons.strategy.ParameterStrategy;
import commons.strategy.RoundRobinStrategy;
import commons.values.RankedValue;
import definitions.ClassInfo;
import definitions.Declaration;
import search.ISText;
import search.SearchReport;
import search.config.SearchConfig;
import tweak.config.TweakConfig;
import types.PolymorphicType;
import types.ReferenceType;
import types.StabileTypeFactory;
import types.Type;

public class InSynthBenchmarksTweakDeclSearch extends ISTextParameterGeneratorAndExampleRunner<SearchReport, RankedValue> {

	private int maxNumberOfSteps;

	public InSynthBenchmarksTweakDeclSearch(ISText iSText, int maxNumberOfSteps, ParameterStrategy<SearchReport, RankedValue> strategy) {
		super(iSText, RankedValue.class, strategy);
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
	protected List<Example<SearchReport, RankedValue>> getExamples(ISText iSText) {
		List<Example<SearchReport, RankedValue>> examples = new LinkedList<Example<SearchReport, RankedValue>>();
		StabileAPI api = iSText.getAPI();
		StabileTypeFactory typeFactory = api.getStf();

		Type[] noArgs = new Type[]{};
		Type fileType = typeFactory.createMonomorphicReferenceType(java.io.File.class);
		Type stringType = typeFactory.createMonomorphicReferenceType(java.lang.String.class);
		Type writerType = typeFactory.createMonomorphicReferenceType(java.io.Writer.class);
		Type intType = typeFactory.createPrimitiveType("int");
		Type readerType = typeFactory.createMonomorphicReferenceType(java.io.Reader.class);
		Type inputStreamType = typeFactory.createMonomorphicReferenceType(java.io.InputStream.class);

		Type intArrayType = typeFactory.createArrayType("int");
		Type boolType = typeFactory.createPrimitiveType("boolean");
		Type fileReaderType = typeFactory.createMonomorphicReferenceType(java.io.FileReader.class);
		Type panelType = typeFactory.createMonomorphicReferenceType(javax.swing.JPanel.class);
		Type frameType = typeFactory.createMonomorphicReferenceType(javax.swing.JFrame.class);
		Type dateFormatType = typeFactory.createMonomorphicReferenceType(java.text.DateFormat.class);
		Type dateFormatterType = typeFactory.createMonomorphicReferenceType(javax.swing.text.DateFormatter.class);
		PolymorphicType arrayObjType = typeFactory.createArrayType(java.lang.Object.class);
		Type fisType = typeFactory.createMonomorphicReferenceType(java.io.FileInputStream.class);
		Type fosType = typeFactory.createMonomorphicReferenceType(java.io.FileOutputStream.class);
		Type isrType = typeFactory.createMonomorphicReferenceType(java.io.InputStreamReader.class);
		Type byteArrayType = typeFactory.createArrayType("byte");
		Type containerType = typeFactory.createMonomorphicReferenceType(java.awt.Container.class);
		Type objType = typeFactory.createMonomorphicReferenceType(java.lang.Object.class);
		Type matrixObjectType = typeFactory.createPolymorphicType("java.lang.Array", new Type[]{arrayObjType});
		Type pipedWriterType = typeFactory.createMonomorphicReferenceType(java.io.PipedWriter.class);
		Type actionListenerType = typeFactory.createMonomorphicReferenceType(java.awt.event.ActionListener.class);
		Type outputStreamType = typeFactory.createMonomorphicReferenceType(java.io.OutputStream.class);

		Local args = new Local("args", stringType);

		examples.add(createDeclSearchExample(java.awt.AWTPermission.class, "\"t\" args", new Local[]{args},
				new Declaration[]{
			cons(java.awt.AWTPermission.class, new Type[]{stringType}, api)
		}));

		examples.add(createDeclSearchExample(java.io.BufferedInputStream.class,"fis args", new Local[]{args, 
			new Local("fis", fisType)},
			new Declaration[]{
			cons(java.io.BufferedInputStream.class, new Type[]{inputStreamType}, api)			
		}));

		examples.add(createDeclSearchExample(java.io.BufferedOutputStream.class,"file numPrimes primes args", new Local[]{args, 
			new Local("numPrimes", intType),
			new Local("primes", intArrayType),
			new Local("file", fosType)},
			new Declaration[]{
			cons(java.io.BufferedOutputStream.class, new Type[]{outputStreamType}, api)
		}));

		examples.add(createDeclSearchExample(java.io.BufferedReader.class,"fr args", new Local[]{args,
			new Local("fr", fileReaderType)},
			new Declaration[]{
			cons(java.io.BufferedReader.class, new Type[]{readerType}, api)
		}));

		examples.add(createDeclSearchExample(java.io.BufferedReader.class,"isr args", new Local[]{args,
			new Local("isr", isrType)},
			new Declaration[]{
			cons(java.io.BufferedReader.class, new Type[]{readerType}, api)
		}));

		examples.add(createDeclSearchExample(java.io.BufferedReader.class,"url args", new Local[]{args,
			new Local("url", typeFactory.createMonomorphicReferenceType(java.net.URL.class))},
			new Declaration[]{
			cons(java.io.BufferedReader.class, new Type[]{readerType}, api),
			//cons(java.io.InputStreamReader.class, new Type[]{inputStreamType}, api),
			//decl(java.net.URL.class, "openStream", noArgs, api),
		}));

		examples.add(createDeclSearchExample(java.io.ByteArrayInputStream.class,"input1 b tmp args", new Local[]{args,
			new Local("input1", typeFactory.createMonomorphicReferenceType(java.io.ByteArrayInputStream.class)),
			new Local("b", byteArrayType),
			new Local("tmp", stringType)},
			new Declaration[]{
			cons(java.io.ByteArrayInputStream.class, new Type[]{byteArrayType, intType, intType}, api)
		}));

		examples.add(createDeclSearchExample(java.io.ByteArrayOutputStream.class,"12 args", new Local[]{args},
				new Declaration[]{
			cons(java.io.ByteArrayOutputStream.class, new Type[]{intType}, api)
		}));

		examples.add(createDeclSearchExample(java.net.DatagramSocket.class,"ia args", new Local[]{args,
			new Local("ia", typeFactory.createMonomorphicReferenceType(java.net.InetAddress.class))},
			new Declaration[]{
			cons(java.net.DatagramSocket.class, noArgs, api)
		}));

		//***
		examples.add(createDeclSearchExample(java.io.DataInputStream.class,"fis args", new Local[]{args,
			new Local("fis", fisType)},
			new Declaration[]{
			cons(java.io.DataInputStream.class, new Type[]{inputStreamType}, api)
		}));

		examples.add(createDeclSearchExample(java.io.DataOutputStream.class,"fos args", new Local[]{args,
			new Local("fos", fosType)},
			new Declaration[]{
			cons(java.io.DataOutputStream.class, new Type[]{outputStreamType}, api)
		}));

		examples.add(createDeclSearchExample(javax.swing.DefaultBoundedRangeModel.class,"args", new Local[]{args},
				new Declaration[]{
			cons(javax.swing.DefaultBoundedRangeModel.class, noArgs, api)
		}));

		examples.add(createDeclSearchExample(java.awt.DisplayMode.class,"canChg gs ge args", new Local[]{args,
			new Local("ge", typeFactory.createMonomorphicReferenceType(java.awt.GraphicsEnvironment.class)),
			new Local("canChg", boolType),
			new Local("gs", typeFactory.createMonomorphicReferenceType(java.awt.GraphicsDevice.class))},
			new Declaration[]{
			decl(java.awt.GraphicsDevice.class,"getDisplayMode", noArgs, api)
		}));

		examples.add(createDeclSearchExample(java.io.FileInputStream.class,"fd inputFile1 aFile args", new Local[]{args,
			new Local("fd", typeFactory.createMonomorphicReferenceType(java.io.FileDescriptor.class)),
			new Local("inputFile1", fisType),
			new Local("aFile", fileType)},
			new Declaration[]{
			cons(java.io.FileInputStream.class, new Type[]{fileType}, api)
		}));

		examples.add(createDeclSearchExample(java.io.FileInputStream.class,"\"fileName.dat\" args", new Local[]{args},
				new Declaration[]{
			cons(java.io.FileInputStream.class, new Type[]{stringType}, api)
		}));

		examples.add(createDeclSearchExample(java.io.FileOutputStream.class,"tempFile args", new Local[]{args,
			new Local("tempFile", fileType)},
			new Declaration[]{
			cons(java.io.FileOutputStream.class, new Type[]{fileType}, api)
		}));

		examples.add(createDeclSearchExample(java.io.FileReader.class,"outputFile inputFile args", new Local[]{args,
			new Local("inputFile", fileType),
			new Local("outputFile", fileType)},
			new Declaration[]{
			cons(java.io.FileReader.class, new Type[]{fileType}, api)
		}));

		examples.add(createDeclSearchExample(java.io.File.class,"\"MainClass.java\" args", new Local[]{args},
				new Declaration[]{
			cons(java.io.File.class, new Type[]{stringType}, api)
		}));

		examples.add(createDeclSearchExample(java.io.FileWriter.class,"in outputFile inputFile args", new Local[]{args,
			new Local("inputFile", fileType),
			new Local("in", fileReaderType),
			new Local("outputFile", fileType)},
			new Declaration[]{
			cons(java.io.FileWriter.class, new Type[]{fileType}, api)
		}));

		examples.add(createDeclSearchExample(java.io.FileWriter.class,"\"LPT1:\" args", new Local[]{args},
				new Declaration[]{
			cons(java.io.FileWriter.class, new Type[]{stringType}, api)
		}));

		examples.add(createDeclSearchExample(java.awt.GridBagConstraints.class,"gridbag", new Local[]{
			new Local("gridbag", typeFactory.createMonomorphicReferenceType(java.awt.GridBagLayout.class))},
			new Declaration[]{
			cons(java.awt.GridBagConstraints.class, noArgs, api)
		}));

		examples.add(createDeclSearchExample(java.awt.GridBagLayout.class, "", new Local[]{},
				new Declaration[]{
			cons(java.awt.GridBagLayout.class, noArgs, api)
		}));

		examples.add(createDeclSearchExample(javax.swing.GroupLayout.class,"panel frame args", new Local[]{args,
			new Local("frame", frameType),
			new Local("panel", panelType)},
			new Declaration[]{
			cons(javax.swing.GroupLayout.class, new Type[]{containerType}, api)
		}));

		examples.add(createDeclSearchExample(javax.swing.ImageIcon.class,"\"yourFile.gif\" args", new Local[]{args},
				new Declaration[]{
			cons(javax.swing.ImageIcon.class, new Type[]{stringType}, api)
		}));

		examples.add(createDeclSearchExample(java.io.InputStreamReader.class,"args", new Local[]{args},
				new Declaration[]{
			cons(java.io.InputStreamReader.class, new Type[]{inputStreamType}, api)
		}));

		examples.add(createDeclSearchExample(javax.swing.JButton.class,"\"Button1\"", new Local[]{},
				new Declaration[]{
			cons(javax.swing.JButton.class, new Type[]{stringType}, api)
		}));

		examples.add(createDeclSearchExample(javax.swing.JCheckBox.class,"\"Anchovies\" border panel frame title args", new Local[]{args,
			new Local("title", stringType),
			new Local("panel", panelType),
			new Local("frame", frameType),
			new Local("border", typeFactory.createMonomorphicReferenceType(javax.swing.border.Border.class))},
			new Declaration[]{
			cons(javax.swing.JCheckBox.class, new Type[]{stringType}, api)
		}));

		//***
		examples.add(createDeclSearchExample(javax.swing.JFormattedTextField.class,"factory editFormatter editFormat displayFormatter displayFormat frame args", new Local[]{args,
			new Local("frame", frameType),
			new Local("displayFormat", dateFormatType),
			new Local("displayFormatter", dateFormatterType),
			new Local("editFormat", dateFormatType),
			new Local("editFormatter", dateFormatterType),
			new Local("factory", typeFactory.createMonomorphicReferenceType(javax.swing.text.DefaultFormatterFactory.class))},
			new Declaration[]{
			consModArgs(javax.swing.JFormattedTextField.class, api)
		}));

		examples.add(createDeclSearchExample(javax.swing.text.MaskFormatter.class,"rowOne f args", new Local[]{args,
			new Local("f", frameType),
			new Local("rowOne", typeFactory.createMonomorphicReferenceType(javax.swing.Box.class))},
			new Declaration[]{
			cons(javax.swing.text.MaskFormatter.class, new Type[]{stringType}, api)
		}));

		//***
		examples.add(createDeclSearchExample(javax.swing.JTable.class,"columns rows f args", new Local[]{args,
			new Local("f", frameType),
			new Local("rows", matrixObjectType),			
			new Local("columns", arrayObjType)},
			new Declaration[]{
			cons(javax.swing.JTable.class, new Type[]{matrixObjectType, arrayObjType}, api)
		}));

		examples.add(createDeclSearchExample(javax.swing.JTextArea.class,"\"Initial Text\" args", new Local[]{args},
				new Declaration[]{
			cons(javax.swing.JTextArea.class, new Type[]{stringType}, api)
		}));

		examples.add(createDeclSearchExample(javax.swing.JFrame.class,"\"JToggleButton Sample\" args", new Local[]{args},
				new Declaration[]{
			cons(javax.swing.JFrame.class, new Type[]{stringType}, api)
		}));

		examples.add(createDeclSearchExample(javax.swing.JTree.class,"f args", new Local[]{args,
			new Local("f", frameType)},
			new Declaration[]{
			cons(javax.swing.JTree.class, noArgs, api)
		}));

		examples.add(createDeclSearchExample(javax.swing.JViewport.class,"scrollPane table frame fname headers rows args", new Local[]{args,
			new Local("frame", frameType),
			new Local("fname", stringType),
			new Local("rows", matrixObjectType),			
			new Local("headers", arrayObjType),
			new Local("table", typeFactory.createMonomorphicReferenceType(javax.swing.JTable.class)),
			new Local("scrollPane", typeFactory.createMonomorphicReferenceType(javax.swing.JScrollPane.class))},
			new Declaration[]{
			cons(javax.swing.JViewport.class, noArgs, api)
		}));

		examples.add(createDeclSearchExample(javax.swing.JWindow.class,"args", new Local[]{args},
				new Declaration[]{
			cons(javax.swing.JWindow.class, noArgs, api)
		}));

		examples.add(createDeclSearchExample(java.io.LineNumberReader.class,"args", new Local[]{args},
				new Declaration[]{
			cons(java.io.LineNumberReader.class, new Type[]{readerType}, api)
		}));

		examples.add(createDeclSearchExample(java.io.ObjectInputStream.class,"fis oos fos list args", new Local[]{args,
			new Local("list", typeFactory.createPolymorphicType(java.util.List.class, new Type[]{stringType})),
			new Local("fos", fosType),
			new Local("fis", fisType),
			new Local("oos", typeFactory.createMonomorphicReferenceType(java.io.ObjectOutputStream.class))},
			new Declaration[]{
			cons(java.io.ObjectInputStream.class, new Type[]{inputStreamType}, api)
		}));

		examples.add(createDeclSearchExample(java.io.ObjectOutputStream.class,"fos list args", new Local[]{args,
			new Local("list", typeFactory.createPolymorphicType(java.util.List.class, new Type[]{stringType})),
			new Local("fos", fosType)},
			new Declaration[]{
			cons(java.io.ObjectOutputStream.class, new Type[]{outputStreamType}, api)
		}));

		examples.add(createDeclSearchExample(java.io.PipedReader.class,"pw args", new Local[]{args,
			new Local("pw", pipedWriterType)},
			new Declaration[]{
			cons(java.io.PipedReader.class, new Type[]{pipedWriterType}, api)
		}));

		examples.add(createDeclSearchExample(java.io.PipedWriter.class,"args", new Local[]{args},
				new Declaration[]{
			cons(java.io.PipedWriter.class, noArgs, api)
		}));

		examples.add(createDeclSearchExample(java.awt.Point.class,"50 25 aPoint args", new Local[]{args,
			new Local("aPoint", typeFactory.createMonomorphicReferenceType(java.awt.Point.class))},
			new Declaration[]{
			cons(java.awt.Point.class, new Type[]{intType, intType}, api)
		}));

		examples.add(createDeclSearchExample(java.io.PrintStream.class,"fout tempFile args", new Local[]{args,
			new Local("tempFile", fileType),
			new Local("fout", fosType)},
			new Declaration[]{
			cons(java.io.PrintStream.class, new Type[]{outputStreamType}, api)
		}));

		examples.add(createDeclSearchExample(java.io.PrintWriter.class,"false bw fw args", new Local[]{args,
			new Local("fw", typeFactory.createMonomorphicReferenceType(java.io.FileWriter.class)),
			new Local("bw", typeFactory.createMonomorphicReferenceType(java.io.BufferedWriter.class))},
			new Declaration[]{
			cons(java.io.PrintWriter.class, new Type[]{writerType, boolType}, api)
		}));

		examples.add(createDeclSearchExample(java.io.SequenceInputStream.class,"false ftwo fone args", new Local[]{args,
			new Local("fone", fisType),
			new Local("ftwo", fisType)},
			new Declaration[]{
			cons(java.io.SequenceInputStream.class, new Type[]{inputStreamType, inputStreamType}, api)
		}));

		examples.add(createDeclSearchExample(java.net.ServerSocket.class,"port args", new Local[]{args,
			new Local("port", intType)},
			new Declaration[]{
			cons(java.net.ServerSocket.class, new Type[]{intType}, api)
		}));

		examples.add(createDeclSearchExample(java.io.StreamTokenizer.class,"br fr args", new Local[]{args,
			new Local("fr", fileReaderType),
			new Local("br", typeFactory.createMonomorphicReferenceType(java.io.BufferedReader.class))},
			new Declaration[]{
			cons(java.io.StreamTokenizer.class, new Type[]{readerType}, api)
		}));

		examples.add(createDeclSearchExample(java.io.StringReader.class,"\"a bc ddd\" args", new Local[]{args},
				new Declaration[]{
			cons(java.io.StringReader.class, new Type[]{stringType}, api)
		}));


		examples.add(createDeclSearchExample(javax.swing.Timer.class,"500 actionListener args", new Local[]{args,
			new Local("actionListener", actionListenerType)},
			new Declaration[]{
			cons(javax.swing.Timer.class, new Type[]{intType, actionListenerType}, api)
		}));

		examples.add(createDeclSearchExample(javax.swing.TransferHandler.class,"s args", new Local[]{
			new Local("s", stringType)},
			new Declaration[]{
			cons(javax.swing.TransferHandler.class, new Type[]{stringType}, api)
		}));

		examples.add(createDeclSearchExample(java.net.URLConnection.class,"\"http://www.yourserver.com\" args", new Local[]{args},
				new Declaration[]{
			cons(java.net.URL.class, new Type[]{stringType}, api),
			decl(java.net.URL.class, "openConnection", noArgs, api)
		}));

		return examples;
	}

	//***
	private Declaration consModArgs(Class<?> clazz, StabileAPI api) {
		return consModArgs(clazz.getName(), api);
	}

	//***
	private Declaration consModArgs(String className, StabileAPI api) {
		ClassInfo clazz = api.getClass(className);
		Declaration[] cons = clazz.getConstructors();
		return cons[0];
	}

	private Declaration declModArgs(Class<?> clazz, String methodName, StabileAPI api) {
		return declModArgs(clazz.getName(), methodName, api);
	}

	private Declaration declModArgs(String className, String methodName, StabileAPI api) {
		ClassInfo clazz = api.getClass(className);
		Declaration[] decls = clazz.getUniqueDeclarations();
		for (Declaration decl : decls) {
			if (methodName.equals(decl.getName())){
				System.out.println(decl);
				return decl;
			}
		}
		throw new RuntimeException("Declaration "+ className+"."+methodName+" not found.");
	}

	private Declaration declP(Class<?> clazz, String methodName, Type[] types, StabileAPI api) {
		return declP(clazz.getName(), methodName, types, api);
	}

	private Declaration declP(String className, String methodName, Type[] argTypes, StabileAPI api) {
		ClassInfo clazz = api.getClass(className);
		Declaration[] decls = clazz.getUniqueDeclarations();
		for (Declaration decl : decls) {
			if (methodName.equals(decl.getName()) && equalsByPrefix(argTypes, decl.getArgTypes())){
				System.out.println(decl);
				return decl;
			}
		}
		throw new RuntimeException("Declaration "+ className+"."+methodName+" not found.");
	}

	private boolean equalsByPrefix(Type[] argTypes, Type[] declArgTypes) {
		int length = argTypes.length;
		if (length != declArgTypes.length) return false;

		for (int i = 0; i < length; i++) {
			if(!argTypes[i].getPrefix().equals(declArgTypes[i])) return false;
		}

		return true;
	}

	private Declaration decl(Class<?> clazz, String methodName, Type[] argTypes, StabileAPI api) {
		return decl(clazz.getName(), methodName, argTypes, api);
	}

	private Declaration cons(Class<?> clazz, Type[] argTypes, StabileAPI api) {
		return cons(clazz.getName(), argTypes, api);
	}

	private Declaration cons(String className, Type[] argTypes, StabileAPI api) {
		ClassInfo clazz = api.getClass(className);
		Declaration[] cons = clazz.getConstructors();
		for (Declaration con : cons) {
			if (Arrays.equals(argTypes, con.getArgTypes())){
				System.out.println(con);
				return con;
			}
		}
		throw new RuntimeException("Constructor "+ className+" not found.");
	}

	private Declaration decl(String className, String methodName, Type[] argTypes, StabileAPI api) {
		ClassInfo clazz = api.getClass(className);
		Declaration[] declarations = clazz.getUniqueDeclarations();
		for (Declaration declaration : declarations) {
			if (methodName.equals(declaration.getName()) && Arrays.equals(argTypes, declaration.getArgTypes())){
				System.out.println(declaration);

				return declaration;
			}
		}
		throw new RuntimeException("Declaration "+ className+"."+methodName+" not found.");
	}

	private Example<SearchReport, RankedValue> createDeclSearchExample(String input, Declaration[] decls) {
		return createDeclSearchExample(input, new TopDeclGoal(input, decls));
	}

	private Example<SearchReport, RankedValue> createDeclSearchExample(String input, Goal<SearchReport, RankedValue> goal) {
		return new DeclSearchExample(input, goal);
	}

	private Example<SearchReport, RankedValue> createDeclSearchExample(Class<?> expectedClass, String inputSufix, Local[] locals, Declaration[] decls) {
		String input = "new"+expectedClass.getSimpleName()+" "+inputSufix;
		return createDeclSearchExample(input, locals, new TopDeclGoal(input, decls));
	}

	private Example<SearchReport, RankedValue> createDeclSearchExample(String input, Local[] locals, Goal<SearchReport, RankedValue> goal) {
		return new DeclSearchExample(input, Arrays.asList(locals), goal);
	}

	@Override
	protected List<ParameterGenerator<?>> getParameterGenerators() {
		List<ParameterGenerator<?>> generators = new LinkedList<ParameterGenerator<?>>();

		generators.add(new DoubleMultiGenerator("kindMatrix[0][0]", 0.50, 0.1, 4) {
			@Override
			public void set() {
				double[][] kindMatrix = SearchConfig.getDeclarationInputKindMatrix();
				kindMatrix[0][0] = this.getVal();
			}
		});

		generators.add(new DoubleMultiGenerator("kindMatrix[0][1]", 0.20, 0.1, 1) {
			@Override
			public void set() {
				double[][] kindMatrix = SearchConfig.getDeclarationInputKindMatrix();
				kindMatrix[0][1] = this.getVal();
			}
		});		

		generators.add(new DoubleMultiGenerator("kindMatrix[1][0]", 0.20, 0.1, 1) {
			@Override
			public void set() {
				double[][] kindMatrix = SearchConfig.getDeclarationInputKindMatrix();
				kindMatrix[1][0] = this.getVal();				
			}
		});

		generators.add(new DoubleMultiGenerator("kindMatrix[1][1]", 0.50, 0.1, 4) {
			@Override
			public void set() {
				double[][] kindMatrix = SearchConfig.getDeclarationInputKindMatrix();
				kindMatrix[1][1] = this.getVal();				
			}
		});		

		generators.add(new DoubleMultiGenerator("DeclarationInputUnmatchingWeight", 0.05, 0.01, 4) {
			@Override
			public void set() {
				SearchConfig.setDeclarationInputUnmatchingWeight(this.getVal());				
			}
		});

		generators.add(new DoubleMultiGenerator("DeclarationScorerCoefs", 0.5, 0.1, 4) {
			@Override
			public void set() {
				double val = this.getVal();
				Double[] coefs = SearchConfig.getDeclarationScorerCoefs();
				coefs[0] = val;
				coefs[1] = 1.0 - val;
			}
		});

		generators.add(new DoubleMultiGenerator("PrimaryWeight", 0.5, 0.1, 4) {
			@Override
			public void set() {
				double val = this.getVal();
				SearchConfig.setPrimaryWeight(val);
				SearchConfig.setSecondaryWeight(1.0 - val);
			}
		});

		generators.add(new DoubleMultiGenerator("RelatedWeigthFactor", 0.5, 0.1, 4) {
			@Override
			public void set() {
				SearchConfig.setRelatedWeigthFactor(this.getVal());
			}
		});

		return generators;
	}

	public static void main(String[] args) {
		TweakConfig config = TweakConfig.getInstance();
		SearchConfig.setMaxSelectedDeclarations(config.getMaxSelectedDeclarations());
		
		InSynthBenchmarksTweakDeclSearch tds = new InSynthBenchmarksTweakDeclSearch(new ISText(config.getSnippetNumISText()),
				config.getIterationNum(), config.<SearchReport, RankedValue>getStrategy());
		tds.tweak();
	}
}
