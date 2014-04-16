package selection.serializers;

import selection.Config;
import selection.IWordExtractor;
import selection.WordExtractorEmpty;
import selection.serializers.config.TargetConfig;
import selection.types.InitialTypeFactory;
import selection.types.NameGenerator;
import selection.types.TypeFactory;

public class TargetSerializerWithoutWords {
	public static void main(String[] args) {
		InitialTypeFactory factory = new InitialTypeFactory(new NameGenerator(Config.getSerializationVariablePrefix()));
		TargetSerializer loader = new TargetSerializer(factory, TargetConfig.getTarget());
		
		IWordExtractor extractor = new WordExtractorEmpty();
		
		loader.serialize(Config.getJarfolder(), Config.getStorageLocation(), extractor);
	
	}
}
