package compositions;

import java.io.File;

import config.Config;

import deserializers.Deserializer;
import api.StabileAPI;
import builders.IBuilder;
import scanners.BoundedScanner;
import types.NameGenerator;

public class CompositionBoundedScanner extends BoundedScanner {
	public static void main(String[] args) {
		NameGenerator nameGen = new NameGenerator(Config.getDeserializerVariablePrefix());
		Deserializer deserializer = new Deserializer();
		StabileAPI api = new StabileAPI(deserializer.deserialize(Config.getStorageLocation()), nameGen);
		
		System.out.println("Classes are added!");
		
		IBuilder builder = new CompositionBuilder(api);
		
		//File input = new File("C:\\Users\\gvero\\git\\nlpcoder\\Benchmarks");
		
		File input = new File(Config.getCompositionCorpusLocation());
		File output = new File(Config.getCompositionStatisticLocation());
		
		scan(builder, input, output, 100000, 10, true);		
	}
}
