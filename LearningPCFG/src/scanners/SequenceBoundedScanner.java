package scanners;

import java.io.File;

import builders.DeclFreqBuilder;
import builders.IBuilder;
import builders.SequenceBuilder;
import selection.Config;
import selection.deserializers.Deserializer;
import selection.types.NameGenerator;
import selection.types.TypeFactory;
import declarations.API;
import definitions.ClassInfo;

public class SequenceBoundedScanner extends BoundedScanner {
	public static void main(String[] args) {
		TypeFactory factory = new TypeFactory(new NameGenerator(Config.getDeserializerVariablePrefix()));
		Deserializer deserializer = new Deserializer(factory);
		ClassInfo[] classes = deserializer.deserialize(Config.getStorageLocation());
		API api = new API(factory);
		api.addClasses(classes);
		
		System.out.println("Classes are added!");
		
		IBuilder builder = new SequenceBuilder(api);
		
		File input = new File("C:\\Users\\gvero\\java_projects\\java_projects");
		File output = new File("sequences.txt");
		
		scan(builder, input, output, 1000000, 10, true);		
	}
}
