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

public class PaperBenchmarksTweakDeclSearch extends ISTextParameterGeneratorAndExampleRunner<SearchReport, RankedValue> {

	private int maxNumberOfSteps;

	public PaperBenchmarksTweakDeclSearch(ISText iSText, int maxNumberOfSteps, ParameterStrategy<SearchReport, RankedValue> strategy) {
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
		
		PaperBenchmarksTweakDeclSearch tds = new PaperBenchmarksTweakDeclSearch(new ISText(config.getSnippetNumISText()), 
				config.getIterationNum(), config.<SearchReport, RankedValue>getStrategy());
		tds.tweak();
	}
}
