import java.io.File;

import config.Config;


import builders.IBuilder;
import builders.LexilizedBuilder;


public class N_LexicalizedScanner extends N_PCFGScanner {

	public static void main(String[] args){
		IBuilder builder = new LexilizedBuilder();
			
		File input = new File("C:\\Users\\gvero\\java_projects\\java_projects");
		File output = setTypeAndGetOutputFile(Config.NAIVE);
		
		scan(builder, input, output, 10, 1);		
	}
	
}
