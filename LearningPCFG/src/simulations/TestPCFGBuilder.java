package simulations;

import java.io.File;

import declarations.API;
import definitions.ClassInfo;

import scanners.BoundedScanner;
import selection.Config;
import selection.deserializers.FullDeserializer;
import selection.types.NameGenerator;
import selection.types.TypeFactory;

import builders.IBuilder;
import builders.PCFGBuilder;

public class TestPCFGBuilder extends BoundedScanner {
	public static void main(String[] args) {
		TypeFactory factory = new TypeFactory(new NameGenerator(Config.getDeserializerVariablePrefix()));
		FullDeserializer deserializer = new FullDeserializer(factory);
		ClassInfo[] classes = deserializer.deserialize(Config.getStorageLocation());
		API api = new API(factory);
		api.addClasses(classes);		
		
		IBuilder builder = new PCFGBuilder(api, factory);

		//File input = new File("C:\\Users\\gvero\\java_projects\\java_projects");

		File input = new File("test");		
		File output = new File("naive.txt");

		scan(builder, input, output, 10, 10);	
	}
}
