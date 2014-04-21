package selection.serializers;

import selection.Config;
import selection.DeclarationParserOne;
import selection.DeclarationParserPipeline;
import selection.GroupWordExtractor;
import selection.IDeclarationParser;
import selection.WordExtractor;
import selection.WordProcessor;
import selection.loaders.ClassInfoLoader;
import selection.loaders.TargetJarLoader;
import selection.serializers.config.TargetConfig;
import selection.types.InitialTypeFactory;
import selection.types.NameGenerator;
import definitions.factory.InitialClassInfoFactory;

public class TargetSerializer {
	
	public static void main(String[] args) {
		InitialTypeFactory factory = new InitialTypeFactory(new NameGenerator(Config.getSerializationVariablePrefix()));
		InitialClassInfoFactory cif = new InitialClassInfoFactory(factory);
		ClassInfoLoader cil = new TargetJarLoader(Config.getMaxFilesToScan(), TargetConfig.getTarget(), cif);
		Serializer loader = new Serializer(cil);
		
		WordProcessor wordProcessor = new WordProcessor();
		WordExtractor extractor = new GroupWordExtractor(new DeclarationParserPipeline(new IDeclarationParser[]{new DeclarationParserOne(wordProcessor)}));
		
		loader.serialize(Config.getJarfolder(), Config.getStorageLocation(), extractor);
	
	}	
}
