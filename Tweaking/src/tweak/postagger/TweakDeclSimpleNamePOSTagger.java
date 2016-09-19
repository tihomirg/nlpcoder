package tweak.postagger;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import nlp.parser.Token;
import api.Local;
import api.StabileAPI;
import commons.ISTextExamplesRunner;
import commons.examples.Example;
import commons.examples.POSTaggerAllLeadingTokensExample;
import commons.scores.Score;
import commons.values.Value;
import definitions.ClassInfo;
import definitions.Declaration;
import search.ISText;
import tweak.config.TweakConfig;
import types.StabileTypeFactory;

public class TweakDeclSimpleNamePOSTagger extends ISTextExamplesRunner<Token, Value> {

	public TweakDeclSimpleNamePOSTagger(ISText iSText) {
		super(iSText, Value.class);
	}

	protected List<Example<Token, Value>> getExamples(ISText iSText) {
		List<Example<Token, Value>> examples = new LinkedList<Example<Token, Value>>();
		StabileAPI api = iSText.getAPI();
		StabileTypeFactory typeFactory = api.getStf();
		
		examples.add(createSimpleNameExample(java.awt.AWTPermission.class, "\"accessClipboard\"", api)); 
		examples.add(createSimpleNameExample(java.io.BufferedInputStream.class, "fis", new Local[]{new Local("fis", typeFactory.createMonomorphicReferenceType(java.io.FileInputStream.class))}, api)); 
		examples.add(createSimpleNameExample(java.io.BufferedOutputStream.class, "file", new Local[]{new Local("file", typeFactory.createMonomorphicReferenceType(java.io.FileOutputStream.class))}, api));
		examples.add(createSimpleNameExample(java.io.BufferedReader.class, "fr", new Local[]{new Local("fr", typeFactory.createMonomorphicReferenceType(java.io.FileReader.class))}, api));
		examples.add(createSimpleNameExample(java.io.BufferedReader.class, "isr", new Local[]{new Local("isr", typeFactory.createMonomorphicReferenceType(java.io.InputStreamReader.class))}, api));
		examples.add(createSimpleNameExample(java.io.BufferedReader.class, "url", new Local[]{new Local("url", typeFactory.createMonomorphicReferenceType(java.net.URL.class))}, api));
		examples.add(createSimpleNameExample(java.io.ByteArrayInputStream.class, "b 0 3", new Local[]{new Local("b", typeFactory.createArrayType("byte"))}, api));
		examples.add(createSimpleNameExample(java.io.ByteArrayOutputStream.class, "12", api));
		examples.add(createSimpleNameExample(java.net.DatagramSocket.class, api));
		examples.add(createSimpleNameExample(java.io.DataInputStream.class, "fis", new Local[]{new Local("fis", typeFactory.createMonomorphicReferenceType(java.io.FileInputStream.class))}, api));

		return examples;
	}

	private POSTaggerAllLeadingTokensExample createSimpleNameExample(Class<?> expectedClass, StabileAPI api){
		return createSimpleNameExample(expectedClass, "", api);
	}
	
	private POSTaggerAllLeadingTokensExample createSimpleNameExample(Class<?> expectedClass, String inputSufix, StabileAPI api){
		return createSimpleNameExample(expectedClass, inputSufix, EMPTY_LOCALS, api);
	}
	
	private POSTaggerAllLeadingTokensExample createSimpleNameExample(Class<?> expectedClass, String inputSufix, Local[] locals, StabileAPI api) {
		ClassInfo clazz = api.getClass(expectedClass.getName());
		Declaration[] constructors = clazz.getConstructors();
		POSTaggerAllLeadingTokensExample example = new POSTaggerAllLeadingTokensExample("new"+expectedClass.getSimpleName()+" "+inputSufix, Arrays.asList(locals), constructors[0].getSimpleNameTokens());
		return example;
	}
	
	public static void main(String[] args) {
		ISTextExamplesRunner<Token, Value> tweakPOSTagger = new TweakDeclSimpleNamePOSTagger(new ISText(TweakConfig.getInstance().getSnippetNumISText()));
		Score<Value> score = tweakPOSTagger.run();
		
		System.out.println(score);
	}
}
