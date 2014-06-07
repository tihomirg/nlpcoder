package all;

import java.io.File;

import config.Config;
import deserializers.Deserializer;
import all.CompositionsAndDeclarationsBuilder;
import api.StabileAPI;
import builders.IBuilder;
import scanners.BoundedScanner;
import types.NameGenerator;

public class CompositionsAndDeclarationsScanner extends BoundedScanner {
	public static void main(String[] args) {
		NameGenerator nameGen = new NameGenerator(Config.getDeserializerVariablePrefix());
		Deserializer deserializer = new Deserializer();
		StabileAPI api = new StabileAPI(deserializer.deserialize(Config.getStorageLocation()), nameGen);
		
		System.out.println("Classes are added!");
		
		IBuilder builder = new CompositionsAndDeclarationsBuilder(api);
		
		//File input = new File("C:\\Users\\gvero\\git\\nlpcoder\\Benchmarks");
		
		File input = new File(Config.getCompositionCorpusLocation());
		File compositionOutput = new File(Config.getCompositionStatisticLocation());
		
		File declOutput = new File(Config.getDeclarationFrequencyLocation());
		scan(builder, input, compositionOutput, declOutput, 100000, 10, true);
		
	}
}
