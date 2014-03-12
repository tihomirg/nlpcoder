import java.io.File;

import builders.PCFGBuilder;
import builders.IBuilder;
import config.Config;


public class N_ExprScanner extends N_PCFGScanner {

		public static void main(String[] args){
			IBuilder builder = new PCFGBuilder();
				
			//File input = new File("C:\\Users\\gvero\\java_projects\\java_projects");
			
			File input = new File("test");
			File output = setTypeAndGetOutputFile(Config.NAIVE);
			
			scan(builder, input, output, 10000, 10);		
		}
		
}
