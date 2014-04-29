package scanners;

import java.io.File;
import java.util.Collection;

import builders.IBuilder;
import builders.SimpleSequenceBuilder;
import selection.Config;
import selection.deserializers.Deserializer;
import selection.types.NameGenerator;
import selection.types.StabileTypeFactory;
import declarations.API;
import definitions.ClassInfo;
import definitions.factory.InitialClassInfoFactory;
import definitions.factory.StabileClassInfoFactory;

public class SequenceBoundedScanner extends BoundedScanner {
	public static void main(String[] args) {
		NameGenerator nameGen = new NameGenerator(Config.getDeserializerVariablePrefix());
		StabileTypeFactory factory = new StabileTypeFactory(nameGen);
		Deserializer deserializer = new Deserializer();
		InitialClassInfoFactory icif = deserializer.deserialize(Config.getStorageLocation());
		
		StabileClassInfoFactory scif = new StabileClassInfoFactory(icif, nameGen);
		
		
		Collection<ClassInfo> classes = deserializer.deserialize(Config.getStorageLocation()).getClasses();
		API api = new API(factory);
		api.addClasses(classes);
		
		System.out.println("Classes are added!");
		
		IBuilder builder = new SimpleSequenceBuilder(api);
		
		File input = new File("C:\\Users\\gvero\\java_projects\\java_projects");
		File output = new File("sequences.txt");
		
		scan(builder, input, output, 1000000, 10, true);		
	}
}
