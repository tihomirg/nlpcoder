package merging;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import merging.core.HandlerTable;
import definitions.Declaration;
import statistics.parsers.DoubleResult;
import statistics.parsers.IntResult;
import statistics.parsers.Parser;
import statistics.posttrees.Expr;
import synthesis.handlers.Handler;
import types.StabileTypeFactory;

public class CompositionStatistics {

	private StabileTypeFactory tf;
	private String fileName;
	private Map<Integer, Declaration> decls;

	private HandlerTable table;
	
	private double min = Double.MAX_VALUE;
	private double max = Double.MIN_VALUE;
	private double compositionWeightFactor;

	public CompositionStatistics(StabileTypeFactory tf, Map<Integer, Declaration> decls, String fileName, HandlerTable table, double compositionWeightFactor) {
		this.tf = tf;
		this.decls = decls;
		this.fileName = fileName;
		this.table = table;
		this.compositionWeightFactor = compositionWeightFactor;

		Parser.setDecls(decls);
		Parser.setTf(tf);
		Parser.init();
	}

	public void read(){
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(this.fileName));
			String line = null;

			while ((line = br.readLine()) != null) {
				parse(line);
			}
			
			System.out.println("Compositional statistics is loaded.");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void parse(String line) {
		try {
			DoubleResult result = Parser.getStatistics(line);

			double value = result.getDouble();

			Expr expr = Parser.parse(result.getRest());

			double score = this.compositionWeightFactor * (1 + Math.log10(value)/10);
			
			trySetMin(score);
			trySetMax(score);
			
			expr.setScore(score);
			Handler handler = expr.getHandler(table);
			
			handler.add(expr);

			//System.out.println(expr);

		} catch(Exception e){
			//e.printStackTrace();
		}
	}
	
	private void trySetMax(double score) {
		if(score > this.max) this.max = score;
	}

	private void trySetMin(double score) {
		if(score < this.min) this.min = score;
	}

	@Override
	public String toString() {
		return "min = "+this.min +"  max = "+this.max;
	}
}
