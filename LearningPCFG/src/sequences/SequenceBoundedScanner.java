package sequences;

import java.io.File;
import api.StabileAPI;
import builders.IBuilder;
import builders.SimpleSequenceBuilder;
import scanners.BoundedScanner;
import selection.Config;
import selection.deserializers.Deserializer;
import selection.types.NameGenerator;

public class SequenceBoundedScanner extends BoundedScanner {
	public static void main(String[] args) {
		NameGenerator nameGen = new NameGenerator(Config.getDeserializerVariablePrefix());
		Deserializer deserializer = new Deserializer();
		StabileAPI api = new StabileAPI(deserializer.deserialize(Config.getStorageLocation()), nameGen);
		
		System.out.println("Classes are added!");
		
		IBuilder builder = new SimpleSequenceBuilder(api);
		
		File input = new File("C:\\Users\\gvero\\java_projects\\java_projects");
		File output = new File("sequences.txt");
		
		scan(builder, input, output, 1000000, 10, true);		
	}
}
