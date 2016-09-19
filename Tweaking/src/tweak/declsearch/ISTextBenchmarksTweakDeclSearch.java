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

public class ISTextBenchmarksTweakDeclSearch extends ISTextParameterGeneratorAndExampleRunner<SearchReport, RankedValue> {

	private int maxNumberOfSteps;

	public ISTextBenchmarksTweakDeclSearch(ISText iSText, int maxNumberOfSteps, ParameterStrategy<SearchReport, RankedValue> strategy) {
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
		Type intType = typeFactory.createPrimitiveType("int");
		Type readerType = typeFactory.createMonomorphicReferenceType(java.io.Reader.class);
		Type inputStreamType = typeFactory.createMonomorphicReferenceType(java.io.InputStream.class);

		Declaration consFileString = cons(java.io.File.class, new Type[]{stringType}, api);
		Declaration declReadFileToString = decl("org.apache.commons.io.FileUtils", "readFileToString", new Type[]{fileType, stringType}, api);
		Declaration declCurrentThread = decl(java.lang.Thread.class, "currentThread", noArgs, api);
		Declaration consGregorianCalendar = cons(java.util.GregorianCalendar.class, noArgs, api);
		Declaration consCalendarYear = decl(java.util.Calendar.class, "YEAR", noArgs, api);
		Declaration declCalendarGet = decl(java.util.Calendar.class, "get", new Type[]{intType}, api);
		Declaration consFileInputStream = cons(java.io.FileInputStream.class, new Type[]{stringType}, api);
		Declaration consBufferedInputStream = cons(java.io.BufferedInputStream.class, new Type[]{inputStreamType}, api);
		Declaration declSetPriority = decl(java.lang.Thread.class, "setPriority", new Type[]{intType}, api);


		examples.add(createDeclSearchExample("copy file fname to destination", 
				new Local[]{new Local("fname", stringType), new Local("destination", stringType)},
				new Declaration[]{
				decl("org.apache.commons.io.FileUtils", "copyFile", new Type[]{fileType, fileType}, api),
//				consFileString
		}));
		
		examples.add(createDeclSearchExample("does x begin with y", 
				new Local[]{new Local("x", stringType), new Local("y", stringType)},
				new Declaration[]{
				decl(java.lang.String.class, "startsWith", new Type[]{stringType}, api),
//				consFileString
		}));
		
		examples.add(createDeclSearchExample("load class \"t\"",
				new Declaration[]{
				decl(java.lang.ClassLoader.class, "loadClass", new Type[]{stringType}, api)
		}));

		examples.add(createDeclSearchExample("make file",
				new Declaration[]{
				decl(java.io.File.class, "createNewFile", noArgs, api),
//				consFileString
		}));

		examples.add(createDeclSearchExample("write \"t1\" to file \"t2\"",
				new Declaration[]{
				//				  cons(java.io.BufferedWriter.class, new Type[]{writerType}, api),
				//				  cons(java.io.FileWriter.class, new Type[]{stringType}, api),
				decl("org.apache.commons.io.FileUtils", "writeStringToFile", new Type[]{fileType, stringType}, api)
//				consFileString,
//				cons(java.io.FileWriter.class, new Type[]{stringType}, api),
//				decl(java.io.Writer.class, "write", new Type[]{stringType}, api)
		}));

		examples.add(createDeclSearchExample("readFile(\"t1\",\"t2\")", 
				new Declaration[]{
				declReadFileToString
//				consFileString
		}));

		
		examples.add(createDeclSearchExample("read file \"t\"", 
				new Declaration[]{
				declReadFileToString
//				consFileString
		}));

		examples.add(createDeclSearchExample("open file \"t\"", 
				new Declaration[]{
				decl("org.apache.commons.io.FileUtils", "openOutputStream", new Type[]{fileType}, api)
//				consFileString
		}));

		examples.add(createDeclSearchExample("parse \"t\"", 
				new Declaration[]{
				decl(java.lang.Integer.class, "parseInt", new Type[]{stringType}, api)
		}));

		examples.add(createDeclSearchExample("substring \"t\" 1", 
				new Declaration[]{
				decl(java.lang.String.class, "substring", new Type[]{intType}, api)
		}));

		examples.add(createDeclSearchExample("new buffered stream \"t\"", 
				new Declaration[]{
				cons(java.io.BufferedReader.class, new Type[]{readerType}, api),
				cons(java.io.InputStreamReader.class, new Type[]{inputStreamType}, api),
				consBufferedInputStream				
//				consFileInputStream
		}));

		examples.add(createDeclSearchExample("get a current year from a calendar", 
				new Declaration[]{
				declCalendarGet,
				consCalendarYear
//				consGregorianCalendar
		}));

		examples.add(createDeclSearchExample("leap year", 
				new Declaration[]{
				decl(java.util.GregorianCalendar.class, "isLeapYear", new Type[]{intType}, api)
//				decl(java.util.Calendar.class, "getInstance", noArgs, api),
//				declCalendarGet,
//				consCalendarYear,
//				consGregorianCalendar
		}));

		examples.add(createDeclSearchExample("current time", 
				new Declaration[]{
				decl(java.lang.System.class, "currentTimeMillis", noArgs, api)
		}));

		examples.add(createDeclSearchExample("open connection \"t\"", 
				new Declaration[]{
//				cons(java.net.URL.class, new Type[]{stringType}, api),
				decl(java.net.URL.class, "openConnection",noArgs, api),
		}));		

		examples.add(createDeclSearchExample("new socket \"t\" 80", 
				new Declaration[]{
				cons(java.net.Socket.class, new Type[]{stringType, intType}, api),
		}));

		examples.add(createDeclSearchExample("put \"t1\" \"t2\" pair into a map", 
				new Declaration[]{
//				cons(java.util.HashMap.class, noArgs, api),
				declModArgs(java.util.AbstractMap.class, "put", api)
		}));

		examples.add(createDeclSearchExample("set thread max priority", 
				new Declaration[]{
//				declCurrentThread,
				declSetPriority,
				decl(java.lang.Thread.class, "MAX_PRIORITY", noArgs, api),
		}));

		examples.add(createDeclSearchExample("get property \"t\"", 
				new Declaration[]{
//				cons(java.util.Properties.class, noArgs, api),
				decl(java.util.Properties.class, "getProperty", new Type[]{stringType}, api)
		}));

		examples.add(createDeclSearchExample("does a file \"t\" exists", 
				new Declaration[]{
//				consFileString,
				decl(java.io.File.class, "exists", noArgs, api)
		}));

		examples.add(createDeclSearchExample("min 1 3", 
				new Declaration[]{
				decl(java.lang.Math.class, "min", new Type[]{intType, intType}, api)
		}));

		examples.add(createDeclSearchExample("get thread id", 
				new Declaration[]{
//				declCurrentThread,
				decl(java.lang.Thread.class, "getId", noArgs, api)
		}));

		examples.add(createDeclSearchExample("join threads", 
				new Declaration[]{
//				declCurrentThread,
				decl(java.lang.Thread.class, "join", noArgs, api)
		}));

		examples.add(createDeclSearchExample("delete file", 
				new Declaration[]{
//				consFileString,
				decl(java.io.File.class, "delete", noArgs, api)
		}));

		examples.add(createDeclSearchExample("print exception stack trace", 
				new Declaration[]{
//				cons(java.lang.Exception.class, new Type[]{stringType}, api),
				decl(java.lang.Throwable.class, "printStackTrace", new Type[]{typeFactory.createMonomorphicReferenceType(java.io.PrintStream.class)}, api)
		}));

		examples.add(createDeclSearchExample("is file \"t\" directory", 
				new Declaration[]{
//				consFileString,
				decl(java.io.File.class, "isDirectory", noArgs, api)
		}));

		examples.add(createDeclSearchExample("get thread stack trace", 
				new Declaration[]{
//				declCurrentThread,
				decl(java.lang.Thread.class, "getStackTrace", noArgs, api)
		}));

		examples.add(createDeclSearchExample("read file \"t\", line by line", 
				new Declaration[]{
//				cons(java.io.LineNumberReader.class, new Type[]{readerType}, api),
//				cons(java.io.FileReader.class, new Type[]{stringType}, api),
				decl(java.io.BufferedReader.class, "readLine", noArgs, api)
		}));

		examples.add(createDeclSearchExample("set time zone to \"t\"", 
				new Declaration[]{
//				decl(java.util.Calendar.class, "getInstance", new Type[]{}, api),
				decl(java.util.Calendar.class,"setTimeZone", new Type[]{typeFactory.createMonomorphicReferenceType(java.util.TimeZone.class)}, api),
//				decl(java.util.TimeZone.class,"getTimeZone", new Type[]{stringType}, api)
		}));

		examples.add(createDeclSearchExample("empty map", 
				new Declaration[]{
				decl(java.util.Collections.class, "emptyMap", noArgs, api)
		}));

		examples.add(createDeclSearchExample("pi", 
				new Declaration[]{
				decl(java.lang.Math.class, "PI", noArgs, api)
		}));

		examples.add(createDeclSearchExample("split \"t1\" with \"t2\"", 
				new Declaration[]{
				decl(java.lang.String.class, "split", new Type[]{stringType}, api)
		}));

		examples.add(createDeclSearchExample("memory", 
				new Declaration[]{
				decl(java.lang.Runtime.class, "freeMemory", noArgs, api),
//				decl(java.lang.Runtime.class, "totalMemory", noArgs, api)
		}));

		examples.add(createDeclSearchExample("free memory", 
				new Declaration[]{
				decl(java.lang.Runtime.class, "freeMemory", noArgs, api)
		}));

		examples.add(createDeclSearchExample("total memory", 
				new Declaration[]{
				decl(java.lang.Runtime.class, "totalMemory", noArgs, api)
		}));

		examples.add(createDeclSearchExample("exec \"t\"", 
				new Declaration[]{
				decl(java.lang.Runtime.class, "exec", new Type[]{stringType}, api)
		}));

		examples.add(createDeclSearchExample("new data stream \"t\"", 
				new Declaration[]{
				cons(java.io.DataInputStream.class, new Type[]{inputStreamType}, api),
//				consFileInputStream
		}));

		examples.add(createDeclSearchExample("rename file \"t1\" to \"t2\"", 
				new Declaration[]{
//				consFileString,
//				consFileString,
				decl(java.io.File.class, "renameTo", new Type[]{fileType}, api)
		}));


		examples.add(createDeclSearchExample("concat \"t1\" \"t2\"", 
				new Declaration[]{
				decl(java.lang.String.class, "concat", new Type[]{stringType}, api)
		}));

		ReferenceType outputStreamType = typeFactory.createMonomorphicReferenceType(java.io.OutputStream.class);
		examples.add(createDeclSearchExample("write a utf \"t1\" to a file \"t2\"", 
				new Declaration[]{
//				cons(java.io.DataOutputStream.class, new Type[]{outputStreamType}, api),
//				cons(java.io.BufferedOutputStream.class, new Type[]{outputStreamType}, api),
//				cons(java.io.FileOutputStream.class, new Type[]{fileType}, api),
//				consFileString,
				decl(java.io.DataOutput.class, "writeUTF", new Type[]{stringType}, api)
		}));

		examples.add(createDeclSearchExample("java home", 
				new Declaration[]{
				decl("org.apache.commons.lang.SystemUtils", "getJavaHome", noArgs, api)
		}));

		examples.add(createDeclSearchExample("lower case", 
				new Declaration[]{
				decl(java.lang.String.class, "toLowerCase", noArgs, api)
		}));

		examples.add(createDeclSearchExample("upper(\"t\")", 
				new Declaration[]{
				decl(java.lang.String.class, "toUpperCase", noArgs, api)
		}));

		examples.add(createDeclSearchExample("compare \"t1\" \"t2\"", 
				new Declaration[]{
				decl(java.lang.String.class, "compareToIgnoreCase", new Type[]{stringType}, api)
		}));		

		examples.add(createDeclSearchExample("BufferedInput \"t\"", 
				new Declaration[]{
				consBufferedInputStream
//				consFileInputStream
		}));	

		examples.add(createDeclSearchExample("set thread min priority", 
				new Declaration[]{
//				declCurrentThread,
//				declSetPriority,
				decl(java.lang.Thread.class, "MIN_PRIORITY", noArgs, api),
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

	private Example<SearchReport, RankedValue> createDeclSearchExample(String input, Local[] locals, Declaration[] decls) {
		return createDeclSearchExample(input, locals, new TopDeclGoal(input, decls));
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

		generators.add(new DoubleMultiGenerator("kindMatrix[0][0]", 0.7, 0.025, 6) {
			@Override
			public void set() {
				double[][] kindMatrix = SearchConfig.getDeclarationInputKindMatrix();
				kindMatrix[0][0] = this.getVal();
			}
		});
		
		generators.add(new DoubleMultiGenerator("kindMatrix[0][1]", 0.3, 0.025, 4) {
			@Override
			public void set() {
				double[][] kindMatrix = SearchConfig.getDeclarationInputKindMatrix();
				kindMatrix[0][1] = this.getVal();
			}
		});		

		generators.add(new DoubleMultiGenerator("kindMatrix[1][0]", 0.4, 0.025, 4) {
			@Override
			public void set() {
				double[][] kindMatrix = SearchConfig.getDeclarationInputKindMatrix();
				kindMatrix[1][0] = this.getVal();				
			}
		});

		generators.add(new DoubleMultiGenerator("kindMatrix[1][1]", 0.5, 0.025, 6) {
			@Override
			public void set() {
				double[][] kindMatrix = SearchConfig.getDeclarationInputKindMatrix();
				kindMatrix[1][1] = this.getVal();				
			}
		});		
			
		generators.add(new DoubleMultiGenerator("DeclarationInputUnmatchingWeight", 0.00, 0.005, 0) {
			@Override
			public void set() {
				SearchConfig.setDeclarationInputUnmatchingWeight(this.getVal());				
			}
		});
		
		generators.add(new DoubleMultiGenerator("DeclarationScorerCoefs", 0.7, 0.025, 6) {
			@Override
			public void set() {
				double val = this.getVal();
				Double[] coefs = SearchConfig.getDeclarationScorerCoefs();
				coefs[0] = val;
				coefs[1] = 1.0 - val;
			}
		});

		generators.add(new DoubleMultiGenerator("PrimaryWeight", 0.7, 0.025, 6) {
			@Override
			public void set() {
				double val = this.getVal();
				SearchConfig.setPrimaryWeight(val);
				SearchConfig.setSecondaryWeight(1.0 - val);
			}
		});

		generators.add(new DoubleMultiGenerator("RelatedWeigthFactor", 0.9, 0.025, 6) {
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
		
		ISTextBenchmarksTweakDeclSearch tds = new ISTextBenchmarksTweakDeclSearch(new ISText(config.getSnippetNumISText()), 
				config.getIterationNum(), config.<SearchReport, RankedValue>getStrategy());
		tds.tweak();
	}
}
