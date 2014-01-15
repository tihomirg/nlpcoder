import java.io.File;

import lexicalized.builders.LexilizedPCFGBuilder;

import symbol.StateSplitterType;
import builders.IBuilder;


public class N_LexicalizedPCFGScanner extends N_PCFGScanner {

	public static void main(String[] args){
		IBuilder builder = new LexilizedPCFGBuilder();
			
		File input = new File("C:\\Users\\gvero\\java_projects\\java_projects");
		File output = setTypeAndGetOutputFile(StateSplitterType.NAIVE);
		
		scan(builder, input, output);		
	}
	
}
