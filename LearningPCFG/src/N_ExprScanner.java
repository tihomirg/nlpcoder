import java.io.File;

import builders.ExprBuilder;
import builders.IBuilder;
import builders.LexilizedBuilder;
import config.Config;


public class N_ExprScanner extends N_PCFGScanner {

		public static void main(String[] args){
			IBuilder builder = new ExprBuilder();
				
			//File input = new File("C:\\Users\\gvero\\java_projects\\java_projects");
			
			File input = new File("test");
			File output = setTypeAndGetOutputFile(Config.NAIVE);
			
			scan(builder, input, output, 10000, 10);		
		}
		
}
