package statistics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import definitions.Declaration;

import statistics.parsers.IntResult;
import statistics.parsers.Parser;
import statistics.posttrees.Expr;
import types.StabileTypeFactory;

public class CompositionStatistics {

	private StabileTypeFactory tf;
	private String fileName;
	private Map<Integer, Declaration> decls;
	
	public CompositionStatistics(StabileTypeFactory tf, Map<Integer, Declaration> decls, String fileName) {
		this.tf = tf;
		this.decls = decls;
		this.fileName = fileName;
	}

	public void read(){
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(this.fileName));
			String line = null;

			while ((line = br.readLine()) != null) {
				parse(line);
			}
			
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
		IntResult result = Parser.getStatistics(line);
		int value = result.getInteger();
		
		Parser.setDecls(decls);
		
		Expr expr = Parser.parse(result.getRest(), tf);
		
		expr.setFrequency(value);
	}
}
