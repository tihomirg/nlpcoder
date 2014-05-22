package statistics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import core.HandlerTable;

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

	public CompositionStatistics(StabileTypeFactory tf, Map<Integer, Declaration> decls, String fileName, HandlerTable table) {
		this.tf = tf;
		this.decls = decls;
		this.fileName = fileName;
		this.table = table;

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

			expr.setFrequency(value);
			Handler handler = expr.getHandler(table);
			
			handler.add(expr);

			//System.out.println(expr);

		} catch(Exception e){
			//e.printStackTrace();
		}
	}
	
	
}
