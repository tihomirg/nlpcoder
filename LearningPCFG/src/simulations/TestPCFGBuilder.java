package simulations;

import java.io.File;
import java.util.Collection;
import declarations.API;
import definitions.ClassInfo;
import scanners.BoundedScanner;
import selection.Config;
import selection.deserializers.Deserializer;
import selection.types.NameGenerator;
import selection.types.StabileTypeFactory;
import builders.IBuilder;
import builders.PCFGBuilder;

public class TestPCFGBuilder extends BoundedScanner {
	public static void main(String[] args) {
		StabileTypeFactory factory = new StabileTypeFactory(new NameGenerator(Config.getDeserializerVariablePrefix()));
		Deserializer deserializer = new Deserializer();
		Collection<ClassInfo> classes = deserializer.deserialize(Config.getStorageLocation()).getClasses();
		API api = new API(factory);
		api.addClasses(classes);		
		
		IBuilder builder = new PCFGBuilder(api, factory);

		//File input = new File("C:\\Users\\gvero\\java_projects\\java_projects");

		File input = new File("test");		
		File output = new File("naive.txt");

		scan(builder, input, output, 10, 10, true);	
	}
}
