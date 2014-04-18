package scanners;

import java.io.File;

import builders.DeclFreqBuilder;
import builders.IBuilder;
import selection.Config;
import selection.deserializers.Deserializer;
import selection.types.NameGenerator;
import selection.types.StabileTypeFactory;
import selection.types.TypeFactory;
import declarations.API;
import definitions.ClassInfo;
import definitions.factory.StabileClassInfoFactory;

public class DeclFreqBoundedScanner extends BoundedScanner {
	public static void main(String[] args) {
		StabileTypeFactory factory = new StabileTypeFactory(new NameGenerator(Config.getDeserializerVariablePrefix()));
		Deserializer deserializer = new Deserializer(factory);
		ClassInfo[] classes = deserializer.deserialize(Config.getStorageLocation());
		API api = new API(factory);
		api.addClasses(classes);
		
		System.out.println("Classes are added!");
		
		IBuilder builder = new DeclFreqBuilder(api);
		
		File input = new File("C:\\Users\\gvero\\java_projects\\java_projects");
		File output = new File("frequences.txt");
		
		scan(builder, input, output, 100000, 10, true);		
	}
}
