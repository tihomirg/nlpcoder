import java.io.File;

import builders.IBuilder;
import builders.PCFGBuilder;

import symbol.StateSplitterType;


public class N_PCFGScanner extends N_Scanner {

	public static void main(String[] args){
		IBuilder builder = new PCFGBuilder();
			
		File input = new File("C:\\Users\\gvero\\java_projects\\java_projects");
		File output = setTypeAndGetOutputFile(StateSplitterType.NAIVE);
		
		scan(builder, input, output);		
	}
	
	private static File setTypeAndGetOutputFile(int type){
		switch(type){
		  case StateSplitterType.NAIVE : 
			  StateSplitterType.setType(StateSplitterType.NAIVE);
			  return new File("naive.txt");
		  case StateSplitterType.WITH_PARENT: 
			  StateSplitterType.setType(StateSplitterType.WITH_PARENT);	
			  return new File("with_parent.txt");
		  case StateSplitterType.WITH_GRANDAD: 
			  StateSplitterType.setType(StateSplitterType.WITH_GRANDAD);	
			  return new File("with_parent.txt");
		  default: 
			  StateSplitterType.setType(StateSplitterType.NAIVE);	
			  return new File("naive.txt");
		}
	}
}
