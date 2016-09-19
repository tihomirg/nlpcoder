package tweak.postagger;

import java.util.LinkedList;
import java.util.List;

import nlp.parser.Token;
import search.ISText;
import tweak.config.TweakConfig;
import commons.ISTextExamplesRunner;
import commons.examples.Example;
import commons.examples.POSTaggerAllRichTokensExample;
import commons.scores.Score;
import commons.values.Value;

public class TweakInputPOSTagger extends ISTextExamplesRunner<Token, Value> {

	public TweakInputPOSTagger(ISText iSText) {
		super(iSText, Value.class);
	}

	@Override
	protected List<Example<Token, Value>> getExamples(ISText iSText) {
		List<Example<Token, Value>> examples = new LinkedList<Example<Token, Value>>();

		//1-10
		examples.add(createPOSTaggerInputExample("copy file \"t1\" to \"t2\"", "copy/V file/N string/N to/T string/N"));
		examples.add(createPOSTaggerInputExample("open file \"t\"", "open/V file/N string/N"));
		examples.add(createPOSTaggerInputExample("read file \"t\"", "read/V file/N string/N"));
		examples.add(createPOSTaggerInputExample("parse \"t\"", "parse/V string/N"));
		examples.add(createPOSTaggerInputExample("substring \"t\" 1", "substr/V string/N integer/N"));
		examples.add(createPOSTaggerInputExample("new buffered stream \"t\"", "new/J buffer/V stream/N string/N"));  // wish "buffer" was "J"
		examples.add(createPOSTaggerInputExample("get a current year from a calendar", "get/V a/D current/J year/N from/I a/D calendar/N"));
		examples.add(createPOSTaggerInputExample("leap year", "leap/N year/N"));
		examples.add(createPOSTaggerInputExample("current time", "current/J time/N"));
		examples.add(createPOSTaggerInputExample("open connection \"t\"", "open/V connection/N string/N"));
		
		//11-20
		examples.add(createPOSTaggerInputExample("new socket \"t\" 80", "new/J socket/N string/N integer/N"));
		examples.add(createPOSTaggerInputExample("put \"t1\" \"t2\" pair into a map", "put/V string/N string/N pair/N into/I a/D map/N"));
		examples.add(createPOSTaggerInputExample("set thread max priority", "set/V thread/N max/N priority/N"));  //wish "max" was "J"
		examples.add(createPOSTaggerInputExample("get property \"t\"", "get/V property/N string/N"));
		examples.add(createPOSTaggerInputExample("does a file \"t\" exists", "do/V a/D file/N string/N exist/V"));
		examples.add(createPOSTaggerInputExample("min 1 3", "min/N integer/N integer/N"));
		examples.add(createPOSTaggerInputExample("get thread id", "get/V thread/N id/N"));
		examples.add(createPOSTaggerInputExample("join threads", "join/V thread/N"));
		examples.add(createPOSTaggerInputExample("delete file", "delete/V file/N"));
		examples.add(createPOSTaggerInputExample("print exception stack trace", "print/V exception/N stack/V trace/N")); //wish "stack" was "N"

		//21-30
		examples.add(createPOSTaggerInputExample("is file \"t\" directory", "be/V file/N string/N directory/N"));
		examples.add(createPOSTaggerInputExample("get thread stack trace", "get/V thread/N stack/V trace/N")); //wish "stack" was "N"
		examples.add(createPOSTaggerInputExample("read file \"t\", line by line", "read/V file/N string/N ,/, line/N by/I line/N"));
		examples.add(createPOSTaggerInputExample("write \"t1\" to a file \"t2\"", "write/V string/N to/T a/D file/N string/N"));
		examples.add(createPOSTaggerInputExample("set time zone to \"t\"", "set/V time/N zone/N to/T string/N"));
		examples.add(createPOSTaggerInputExample("empty map", "empty/V map/N")); //wish "empty" was "J"
		examples.add(createPOSTaggerInputExample("pi", "pi/N"));
		examples.add(createPOSTaggerInputExample("load class \"t\"", "load/N class/N string/N")); //wish "load" was "V"
		examples.add(createPOSTaggerInputExample("split \"t1\" with \"t2\"", "split/V string/N with/I string/N"));
		examples.add(createPOSTaggerInputExample("memory", "memory/N"));

		//31-40
		examples.add(createPOSTaggerInputExample("free memory", "free/V memory/N"));  //wish "free" was "J"
		examples.add(createPOSTaggerInputExample("total memory", "total/V memory/N")); //wish "total" was "J"
		examples.add(createPOSTaggerInputExample("exec \"t\"", "exec/N string/N"));
		examples.add(createPOSTaggerInputExample("new data stream \"t\"", "new/J datum/N stream/N string/N"));
		examples.add(createPOSTaggerInputExample("rename file \"t1\" to \"t2\"", "rename/V file/N string/N to/T string/N"));
		examples.add(createPOSTaggerInputExample("move file \"t1\" to \"t2\"", "move/V file/N string/N to/T string/N"));
		examples.add(createPOSTaggerInputExample("write a string \"t1\" to a file \"t2\"", "write/V a/D string/N string/N to/T a/D file/N string/N"));
		examples.add(createPOSTaggerInputExample("concat \"t1\" \"t2\"", "concat/N string/N string/N"));
		examples.add(createPOSTaggerInputExample("write a utf \"t1\" to a file \"t2\"", "write/V a/D utf/N string/N to/T a/D file/N string/N"));
		examples.add(createPOSTaggerInputExample("java home", "java/N home/V")); //wish "home" was "N"

		//41-45
		examples.add(createPOSTaggerInputExample("\"t\" lower case", "string/N lower/V case/N"));
		examples.add(createPOSTaggerInputExample("upper (\"t\")", "upper/J string/N"));
		examples.add(createPOSTaggerInputExample("compare \"t1\" \"t2\"","compare/V string/N string/N"));
		examples.add(createPOSTaggerInputExample("BufferedInput \"t\"", "buffer/V input/N string/N")); //wish "buffer" was "J"
		examples.add(createPOSTaggerInputExample("set thread min priority", "set/V thread/N min/N priority/N"));
		
		return examples;
	}

	private Example<Token, Value> createPOSTaggerInputExample(String input, String expectedOuput) {
		return createPOSTaggerInputExample(input, parseTokens(expectedOuput));
	}

	private List<Token> parseTokens(String expectedOuput) {
		List<Token> tokens = new LinkedList<Token>();
		String[] splits = expectedOuput.split(" ");
		
		for (String split : splits) {
			String[] tokenSplits = split.split("/");
			tokens.add(new Token("", tokenSplits[0], tokenSplits[1]));
		}
		
		return tokens;
	}

	private POSTaggerAllRichTokensExample createPOSTaggerInputExample(String input, List<Token> expectedTokens){
		return new POSTaggerAllRichTokensExample(input, expectedTokens);
	}
	
	public static void main(String[] args) {
		ISTextExamplesRunner<Token, Value> tweakPOSTagger = new TweakInputPOSTagger(new ISText(TweakConfig.getInstance().getSnippetNumISText()));
		Score<Value> score = tweakPOSTagger.run();
		
		System.out.println(score);
	}
}
