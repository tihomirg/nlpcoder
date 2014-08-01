package tests;

import static tests.TestUtil.*;

import java.util.List;

import nlp.parser.Token;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import search.WToken;
import search.nlp.parser.DisjointSubgroups;
import search.nlp.parser.ParserForDisjointSubgroups;
import search.nlp.parser.Subgroup;

public class ParserForDisjointSubgroupsTest {

	private static ParserForDisjointSubgroups parserForIndexes;

	@BeforeClass
	public static void beforeClass() {
		parserForIndexes = new ParserForDisjointSubgroups();
	}
	
	@Before
	public void beforeTest() {
		System.out.println();
		System.out.println("Test --------------------------------------------------------------------------------");
		System.out.println();
	}

	@Test
	public void test1() {
		Token[][] tokenss = new Token[][]{
				{createNounT("file")}, 
				{createNounT("line")}, 
				{createNounT("stream")}, 
				{createNounT("file")}, 
				{createNounT("socket")}};

		List<Subgroup> subgroups = createSubgorups(tokenss);
		List<DisjointSubgroups> disjointSubgroupss = parserForIndexes.calculateDisjointSubgroups(subgroups);
		
		for (DisjointSubgroups disjointSubgroups : disjointSubgroupss) {
			System.out.println(disjointSubgroups);
		}
	}
	
	@Test
	public void test2() {
		Token[][] tokenss = new Token[][]{
				{createNounT("file"), createNounT("line")}, 
				{createNounT("line")}, 
				{createNounT("stream")}, 
				{createNounT("file")}, 
				{createNounT("socket")}};

		List<Subgroup> subgroups = createSubgorups(tokenss);
		List<DisjointSubgroups> disjointSubgroupss = parserForIndexes.calculateDisjointSubgroups(subgroups);
		
		for (DisjointSubgroups disjointSubgroups : disjointSubgroupss) {
			System.out.println(disjointSubgroups);
		}
	}
	
	@Test
	public void test3() {
		Token[][] tokenss = new Token[][]{
				{createVerbT("file")}, 
				{createNounT("line")}, 
				{createNounT("stream")}, 
				{createNounT("file")}, 
				{createNounT("socket")}};

		List<Subgroup> subgroups = createSubgorups(tokenss);
		List<DisjointSubgroups> disjointSubgroupss = parserForIndexes.calculateDisjointSubgroups(subgroups);
		
		for (DisjointSubgroups disjointSubgroups : disjointSubgroupss) {
			System.out.println(disjointSubgroups);
		}
	}
	
	@Test
	public void test4() {
		Token[][] tokenss = new Token[][]{
				{createNounT("file"), createNounT("system")}, 
				{createNounT("line"), createNounT("class")}, 
				{createNounT("stream")}, 
				{createNounT("file"), createNounT("line")}, 
				{createNounT("socket")}};

		List<Subgroup> subgroups = createSubgorups(tokenss);
		List<DisjointSubgroups> disjointSubgroupss = parserForIndexes.calculateDisjointSubgroups(subgroups);
		
		for (DisjointSubgroups disjointSubgroups : disjointSubgroupss) {
			System.out.println(disjointSubgroups);
		}
	}
	
	@Test
	public void test5() {
		Token[][] tokenss = new Token[][]{
				{createNounT("file"), createNounT("system")}, 
				{createNounT("line"), createNounT("class")}, 
				{createNounT("stream")}, 
				{createNounT("file"), createNounT("line")}, 
				{createNounT("socket")},
				{createVerbT("file")}};

		List<Subgroup> subgroups = createSubgorups(tokenss);
		List<DisjointSubgroups> disjointSubgroupss = parserForIndexes.calculateDisjointSubgroups(subgroups);
		
		for (DisjointSubgroups disjointSubgroups : disjointSubgroupss) {
			System.out.println(disjointSubgroups);
		}
	}
	
	@Test
	public void test6() {
		Token[][] tokenss = new Token[][]{
				{createNounT("file"), createNounT("system")}, 
				{createNounT("line"), createNounT("class")}, 
				{createNounT("stream")}, 
				{createNounT("file"), createNounT("line")}, 
				{createNounT("socket")},
				{createVerbT("file"), createNounT("socket")}};

		List<Subgroup> subgroups = createSubgorups(tokenss);
		List<DisjointSubgroups> disjointSubgroupss = parserForIndexes.calculateDisjointSubgroups(subgroups);
		
		for (DisjointSubgroups disjointSubgroups : disjointSubgroupss) {
			System.out.println(disjointSubgroups);
		}
	}	
}
