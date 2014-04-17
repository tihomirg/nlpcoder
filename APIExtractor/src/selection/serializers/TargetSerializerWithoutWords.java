package selection.serializers;

import definitions.ClassInfoFactory;
import selection.Config;
import selection.WordExtractor;
import selection.WordExtractorEmpty;
import selection.serializers.config.TargetConfig;
import selection.types.InitialTypeFactory;
import selection.types.NameGenerator;
import selection.types.TypeFactory;

public class TargetSerializerWithoutWords {
	public static void main(String[] args) {
		InitialTypeFactory factory = new InitialTypeFactory(new NameGenerator(Config.getSerializationVariablePrefix()));
		ClassInfoFactory cif = new ClassInfoFactory(factory);
		TargetSerializer loader = new TargetSerializer(cif, TargetConfig.getTarget());
		
		WordExtractor extractor = new WordExtractorEmpty();
		
		loader.serialize(Config.getJarfolder(), Config.getStorageLocation(), extractor);
	
	}
}
