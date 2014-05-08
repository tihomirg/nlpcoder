package scanners;

import java.io.File;
import java.util.Collection;

import config.Config;

import deserializers.Deserializer;

import api.StabileAPI;
import builders.DeclFreqBuilder;
import builders.IBuilder;
import types.NameGenerator;

public class DeclFreqBoundedScanner extends BoundedScanner {
	public static void main(String[] args) {
		Deserializer deserializer = new Deserializer();
		StabileAPI api = new StabileAPI( deserializer.deserialize(Config.getStorageLocation()), new NameGenerator(Config.getDeserializerVariablePrefix()));
		
		System.out.println("Classes are added!");
		
		IBuilder builder = new DeclFreqBuilder(api);
		
		File input = new File("C:\\Users\\gvero\\java_projects\\java_projects");
		File output = new File("frequences.txt");
		
		scan(builder, input, output, 100000, 10, true);		
	}
}
