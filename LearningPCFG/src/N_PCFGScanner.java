import java.io.File;

import config.Config;

import builders.IBuilder;
import builders.BasicBuilder;



public class N_PCFGScanner extends N_Scanner {

	public static void main(String[] args){
		IBuilder builder = new BasicBuilder();
			
		File input = new File("C:\\Users\\gvero\\java_projects\\java_projects");
		File output = setTypeAndGetOutputFile(Config.NAIVE);
		
		scan(builder, input, output);		
	}
	
	protected static File setTypeAndGetOutputFile(int type){
		switch(type){
		  case Config.NAIVE : 
			  Config.setType(Config.NAIVE);
			  return new File("naive.txt");
		  case Config.WITH_PARENT: 
			  Config.setType(Config.WITH_PARENT);	
			  return new File("with_parent.txt");
		  case Config.WITH_GRANDAD: 
			  Config.setType(Config.WITH_GRANDAD);	
			  return new File("with_parent.txt");
		  default: 
			  Config.setType(Config.NAIVE);	
			  return new File("naive.txt");
		}
	}
}
