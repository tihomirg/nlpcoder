import java.io.File;

import builders.PCFGBuilder;
import builders.IBuilder;


public class BoundedPCFGScanner extends BoundedScanner {

		public static void main(String[] args){
			IBuilder builder = new PCFGBuilder();
				
			//File input = new File("C:\\Users\\gvero\\java_projects\\java_projects");
			
			File input = new File("test");
			File output = new File("naive.txt");
			
			scan(builder, input, output, 10, 10);		
		}
}