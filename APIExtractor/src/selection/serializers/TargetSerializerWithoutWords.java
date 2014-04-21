package selection.serializers;

import definitions.factory.InitialClassInfoFactory;
import selection.Config;
import selection.WordExtractor;
import selection.WordExtractorEmpty;
import selection.loaders.ClassInfoLoader;
import selection.loaders.TargetJarLoader;
import selection.serializers.config.TargetConfig;
import selection.types.InitialTypeFactory;
import selection.types.NameGenerator;

public class TargetSerializerWithoutWords {
	public static void main(String[] args) {
		InitialTypeFactory factory = new InitialTypeFactory(new NameGenerator(Config.getSerializationVariablePrefix()));
		InitialClassInfoFactory cif = new InitialClassInfoFactory(factory);
		ClassInfoLoader cil =  new TargetJarLoader(Config.getMaxFilesToScan(), TargetConfig.getTarget(), cif);
		Serializer loader = new Serializer(cil);
		
		WordExtractor extractor = new WordExtractorEmpty();
		
		loader.serialize(Config.getJarfolder(), Config.getStorageLocation(), extractor);
	
	}
}
