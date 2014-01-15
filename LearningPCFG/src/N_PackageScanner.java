import java.io.File;

import builders.IBuilder;
import builders.PackageStatBuilder;

public class N_PackageScanner extends N_Scanner {
	
	public static void main(String[] args){
		IBuilder builder = new PackageStatBuilder();
		
		File input = new File("C:\\Users\\gvero\\java_projects\\java_projects");
		File output = new File("packages.txt");

		scan(builder, input, output);		
	}
}
