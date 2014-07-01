package search.nlp.parser2;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import util.Pair;

public class ParserForLiteralsTest {

	private ParserForLiterals parser;
	
	@Before
	public void before(){
		parser = new ParserForLiterals();
	}
	
	@Test
	public void testString1() {
		List<Pair<Integer, String>> pairs = parser.extractStringLiterals("file \"my\" open \"rest\"");
		
		System.out.println(pairs);
		
		assertEquals(2, pairs.size());
		checkPair(pairs.get(0), "file ".length(), "\"my\"");
		checkPair(pairs.get(1), "file \"my\" open ".length(), "\"rest\"");
	}
	
	@Test
	public void testNum1() {
		List<Pair<Integer, String>> pairs = parser.extractNumberLiterals("file 98 open 1112312");
		
		System.out.println(pairs);
		
		assertEquals(2, pairs.size());
		checkPair(pairs.get(0), "file ".length(), "98");
		checkPair(pairs.get(1), "file 98 open ".length(), "1112312");
	}	
	
	@Test
	public void testBool1() {
		List<Pair<Integer, String>> pairs = parser.extractBoolLiterals("file true open false");
		
		System.out.println(pairs);
		
		assertEquals(2, pairs.size());
		checkPair(pairs.get(0), "file ".length(), "true");
		checkPair(pairs.get(1), "file true open ".length(), "false");
	}	
	
	@Test
	public void testSubStr1() {
		String text = "file \"my\" open \"rest\"";
		List<Pair<Integer, String>> pairs = parser.extractStringLiterals(text);
		String ltext = parser.substituteStrings(text, pairs);
		assertEquals("file String open String", ltext);
	
		System.out.println(pairs);
		
		assertEquals(2, pairs.size());
		checkPair(pairs.get(0), "file ".length(), "\"my\"");
		checkPair(pairs.get(1), "file String open ".length(), "\"rest\"");
	}	
	
	@Test
	public void testString2() {
		List<Pair<Integer, String>> pairs = parser.extractStringLiterals("leave me \"alone with you\" cause we will \"be happy like this\"");
		
		System.out.println(pairs);
		
		assertEquals(2, pairs.size());
		checkPair(pairs.get(0), "leave me ".length(), "\"alone with you\"");
		checkPair(pairs.get(1), "leave me \"alone with you\" cause we will ".length(), "\"be happy like this\"");
	}
	
	@Test
	public void testNum2() {
		List<Pair<Integer, String>> pairs = parser.extractNumberLiterals("leave me 900 cause we will 500");
		
		System.out.println(pairs);
		
		assertEquals(2, pairs.size());
		checkPair(pairs.get(0), "leave me ".length(), "900");
		checkPair(pairs.get(1), "leave me 900 cause we will ".length(), "500");
	}
	
	@Test
	public void testBool2() {
		List<Pair<Integer, String>> pairs = parser.extractBoolLiterals("leave me true cause we will false");
		
		System.out.println(pairs);
		
		assertEquals(2, pairs.size());
		checkPair(pairs.get(0), "leave me ".length(), "true");
		checkPair(pairs.get(1), "leave me true cause we will ".length(), "false");
	}	
	
	@Test
	public void testSubStr2() {
		String text = "leave me \"alone with you\" cause we will \"be happy like this\"";
		List<Pair<Integer, String>> pairs = parser.extractStringLiterals(text);
		String ltext = parser.substituteStrings(text, pairs);
		assertEquals("leave me String cause we will String", ltext);
	
		System.out.println(pairs);
		
		assertEquals(2, pairs.size());
		checkPair(pairs.get(0), "leave me ".length(), "\"alone with you\"");
		checkPair(pairs.get(1), "leave me String cause we will ".length(), "\"be happy like this\"");
	}	
	
	@Test
	public void testString3() {
		List<Pair<Integer, String>> pairs = parser.extractStringLiterals("\"start with zero\" and \"end with I don't know\"");
		
		System.out.println(pairs);
		
		assertEquals(2, pairs.size());
		checkPair(pairs.get(0), 0, "\"start with zero\"");
		checkPair(pairs.get(1), "\"start with zero\" and ".length(), "\"end with I don't know\"");
	}
	
	@Test
	public void testNum3() {
		List<Pair<Integer, String>> pairs = parser.extractNumberLiterals("893 and 0");
		
		System.out.println(pairs);
		
		assertEquals(2, pairs.size());
		checkPair(pairs.get(0), 0, "893");
		checkPair(pairs.get(1), "893 and ".length(), "0");
	}	
	
	@Test
	public void testBool3() {
		List<Pair<Integer, String>> pairs = parser.extractBoolLiterals("false and true");
		
		System.out.println(pairs);
		
		assertEquals(2, pairs.size());
		checkPair(pairs.get(0), 0, "false");
		checkPair(pairs.get(1), "false and ".length(), "true");
	}	
	
	@Test
	public void testSubStr3() {
		String text = "\"start with zero\" and \"end with I don't know\"";
		List<Pair<Integer, String>> pairs = parser.extractStringLiterals(text);
		String ltext = parser.substituteStrings(text, pairs);
		assertEquals("String and String", ltext);
	
		System.out.println(pairs);
		
		assertEquals(2, pairs.size());
		checkPair(pairs.get(0), 0, "\"start with zero\"");
		checkPair(pairs.get(1), "String and ".length(), "\"end with I don't know\"");
	}	

	@Test
	public void testString4() {
		List<Pair<Integer, String>> pairs = parser.extractStringLiterals("\"starts\"\"together\"");
		
		System.out.println(pairs);
		
		assertEquals(2, pairs.size());
		checkPair(pairs.get(0), 0, "\"starts\"");
		checkPair(pairs.get(1), "\"starts\"".length(), "\"together\"");
	}
	
	@Test
	public void testNum4() {
		List<Pair<Integer, String>> pairs = parser.extractNumberLiterals("0");
		
		System.out.println(pairs);
		
		assertEquals(1, pairs.size());
		checkPair(pairs.get(0), 0, "0");
	}	
	
	@Test
	public void testBool4() {
		List<Pair<Integer, String>> pairs = parser.extractBoolLiterals("true");
		
		System.out.println(pairs);
		
		assertEquals(1, pairs.size());
		checkPair(pairs.get(0), 0, "true");
	}	
	
	@Test
	public void testBool5() {
		List<Pair<Integer, String>> pairs = parser.extractBoolLiterals("truef");
		
		System.out.println(pairs);
		
		assertEquals(0, pairs.size());
	}	
	
	@Test
	public void testBool6() {
		List<Pair<Integer, String>> pairs = parser.extractBoolLiterals("ftrue");
		
		System.out.println(pairs);
		
		assertEquals(0, pairs.size());
	}	
	
	@Test
	public void testBool7() {
		List<Pair<Integer, String>> pairs = parser.extractBoolLiterals("truefalsefalse");
		
		System.out.println(pairs);
		
		assertEquals(0, pairs.size());
	}
	
	@Test
	public void testNegNum1() {
		List<Pair<Integer, String>> pairs = parser.extractNumberLiterals("-1");
		
		System.out.println(pairs);
		
		assertEquals(1, pairs.size());
		checkPair(pairs.get(0), 0, "-1");
	}	
	
	@Test
	public void testNegNum2() {
		List<Pair<Integer, String>> pairs = parser.extractNumberLiterals("--1");
		
		System.out.println(pairs);
		
		assertEquals(1, pairs.size());
		checkPair(pairs.get(0), "-".length(), "-1");
	}	
	
	@Test
	public void testSubStr4() {
		String text = "\"starts\"\"together\"";
		List<Pair<Integer, String>> pairs = parser.extractStringLiterals(text);
		String ltext = parser.substituteStrings(text, pairs);
		assertEquals("StringString", ltext);
	
		System.out.println(pairs);
		
		assertEquals(2, pairs.size());
		checkPair(pairs.get(0), 0, "\"starts\"");
		checkPair(pairs.get(1), "String".length(), "\"together\"");
	}
	
	@Test
	public void testInput1(){
		String text = "open file \"text.txt\" false 90";
		
		Input input = parser.parse(new Input(text));
		
		assertEquals("open file String Boolean Integer", input.getLiteralizedText());
		
		List<Pair<Integer, String>> bools = input.getBooleanLiterals();
		List<Pair<Integer, String>> numbers = input.getNumberLiterals();
		List<Pair<Integer, String>> strings = input.getStringLiterals();
		
		assertEquals(1, bools.size());
		checkPair(bools.get(0), "open file String ".length(), "false");
		
		assertEquals(1, numbers.size());
		checkPair(numbers.get(0), "open file String Boolean ".length(), "90");
		
		assertEquals(1, strings.size());
		checkPair(strings.get(0), "open file ".length(), "\"text.txt\"");
	}

	private void checkPair(Pair<Integer, String> pair, int index, String string) {
		int pairIndex = pair.getFirst();
		String pairstring = pair.getSecond();
		assertEquals(index, pairIndex);
		assertEquals(string, pairstring);
	}
}
