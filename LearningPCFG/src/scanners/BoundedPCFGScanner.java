package scanners;
import java.io.File;

import selection.Config;
import selection.types.NameGenerator;
import selection.types.StabileTypeFactory;
import selection.types.TypeFactory;

import declarations.API;

import builders.PCFGBuilder;
import builders.IBuilder;


public class BoundedPCFGScanner extends BoundedScanner {

		public static void main(String[] args){
			TypeFactory factory = new StabileTypeFactory(new NameGenerator(Config.getDeserializerVariablePrefix()));
			IBuilder builder = new PCFGBuilder(new API(factory), factory);
				
			//File input = new File("C:\\Users\\gvero\\java_projects\\java_projects");
			
			File input = new File("test");
			File output = new File("naive.txt");
			
			scan(builder, input, output, 10, 10, true);		
		}
}
