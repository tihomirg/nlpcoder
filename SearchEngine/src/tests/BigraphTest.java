package tests;

import static org.junit.Assert.*;
import static tests.TestUtil.*;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import search.WToken;
import search.nlp.parser.Subgroup;
import search.scorers.Bigraph;

public class BigraphTest {
	private static double[][] kindMatrix;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		kindMatrix = new double[][]{{1.0, 0.5},{0.5, 1.0}};
	}

	@Test
	public void test1() {
		WToken[][] subgroupWTokenss = new WToken[][]{
				{createNounWT("file",   0, 1.0, 0, 1.0)}, 
				{createNounWT("line",   1, 0.5, 1, 1.0)}, 
				{createNounWT("stream", 1, 0.5, 2, 1.0)}, 
				{createNounWT("file",   1, 0.5, 3, 1.0)}, 
				{createNounWT("socket", 1, 0.5, 4, 1.0)}};

		WToken[] declWTokens = new WToken[]{createNounWT("line", 0, 1.0, 0, 1.0)};
		List<Subgroup> subgroups = TestUtil.createSubgorups(subgroupWTokenss);		
		
		Bigraph bigraph = new Bigraph(subgroups, Arrays.asList(declWTokens), kindMatrix);
		
		assertEquals(0.25, bigraph.calculate(), 0.0);
	}
	
	@Test
	public void test2() {
		WToken[][] subgroupWTokenss = new WToken[][]{
				{createNounWT("file",   0, 1.0, 0, 1.0)}, 
				{createNounWT("line",   1, 0.5, 1, 1.0), createNounWT("line", 1, 0.7, 1, 1.0)}, 
				{createNounWT("stream", 1, 0.5, 2, 1.0)}, 
				{createNounWT("file",   1, 0.5, 3, 1.0)}, 
				{createNounWT("socket", 1, 0.5, 4, 1.0)}};

		WToken[] declWTokens = new WToken[]{createNounWT("line", 0, 1.0, 0, 1.0)};
		List<Subgroup> subgroups = TestUtil.createSubgorups(subgroupWTokenss);		
		
		Bigraph bigraph = new Bigraph(subgroups, Arrays.asList(declWTokens), kindMatrix);
		
		assertEquals(0.35, bigraph.calculate(), 0.0);
	}	

	@Test
	public void test3() {
		WToken[][] subgroupWTokenss = new WToken[][]{
				{createNounWT("file",   0, 1.0, 0, 1.0)}, 
				{createNounWT("line",   1, 0.5, 1, 1.0), createNounWT("line", 1, 0.7, 1, 1.0)}, 
				{createNounWT("stream", 1, 0.5, 2, 1.0)}, 
				{createNounWT("line",   1, 2.0, 3, 1.0)}, 
				{createNounWT("socket", 1, 0.5, 4, 1.0)}};

		WToken[] declWTokens = new WToken[]{createNounWT("line", 0, 1.0, 0, 1.0)};
		List<Subgroup> subgroups = TestUtil.createSubgorups(subgroupWTokenss);		
		
		Bigraph bigraph = new Bigraph(subgroups, Arrays.asList(declWTokens), kindMatrix);
		
		assertEquals(1.0, bigraph.calculate(), 0.0);
	}
	
	@Test
	public void test4() {
		WToken[][] subgroupWTokenss = new WToken[][]{
				{createNounWT("file",   0, 1.0, 0, 1.0)}, 
				{createNounWT("line",   1, 0.5, 1, 1.0), createNounWT("line", 1, 0.7, 1, 1.0)}, 
				{createNounWT("stream", 1, 0.5, 2, 1.0)}, 
				{createNounWT("line",   1, 2.0, 3, 1.0)}, 
				{createNounWT("line",   1, 2.0, 4, 1.0)}, 
				{createNounWT("socket", 1, 0.5, 5, 1.0)}};

		WToken[] declWTokens = new WToken[]{
				createNounWT("line", 0, 1.0, 0, 1.0),
				createNounWT("line", 0, 5.0, 0, 1.0)};
		List<Subgroup> subgroups = TestUtil.createSubgorups(subgroupWTokenss);		
		
		Bigraph bigraph = new Bigraph(subgroups, Arrays.asList(declWTokens), kindMatrix);
		
		assertEquals(6.0, bigraph.calculate(), 0.0);
	}	
	
	@Test
	public void test5() {
		WToken[][] subgroupWTokenss = new WToken[][]{
				{createNounWT("file",   0, 1.0, 0, 1.0)}, 
				{createNounWT("line",   1, 4.0, 1, 1.0), createNounWT("line", 1, 0.7, 1, 1.0)}, 
				{createNounWT("stream", 1, 0.5, 2, 1.0)}, 
				{createNounWT("line",   1, 2.0, 3, 1.0)}, 
				{createNounWT("line",   1, 2.0, 4, 1.0)}, 
				{createNounWT("socket", 1, 0.5, 5, 1.0)}};

		WToken[] declWTokens = new WToken[]{
				createNounWT("line", 0, 1.0, 0, 1.0),
				createNounWT("line", 0, 5.0, 0, 1.0)};
		List<Subgroup> subgroups = TestUtil.createSubgorups(subgroupWTokenss);		
		
		Bigraph bigraph = new Bigraph(subgroups, Arrays.asList(declWTokens), kindMatrix);
		
		assertEquals(11.0, bigraph.calculate(), 0.0);
	}
	
}
