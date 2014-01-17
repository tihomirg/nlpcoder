import java.io.File;

import lexicalized.builders.LexilizedPCFGBuilder;
import builders.IBuilder;
import config.Config;
import exprcollectors.builders.PCFGBuilder;


public class N_ExprScanner extends N_PCFGScanner {

		public static void main(String[] args){
			IBuilder builder = new PCFGBuilder();
				
			File input = new File("C:\\Users\\gvero\\java_projects\\java_projects");
			File output = setTypeAndGetOutputFile(Config.NAIVE);
			
			scan(builder, input, output, 1, 1);		
		}
		
}
