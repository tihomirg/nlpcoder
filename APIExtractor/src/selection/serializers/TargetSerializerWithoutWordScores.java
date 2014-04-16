package selection.serializers;

import selection.Config;
import selection.DeclarationParserOne;
import selection.DeclarationParserPipeline;
import selection.GroupWordExtractor;
import selection.IDeclarationParser;
import selection.IWordExtractor;
import selection.WordProcessor;
import selection.serializers.config.TargetConfig;
import selection.types.InitialTypeFactory;
import selection.types.NameGenerator;
import selection.types.TypeFactory;

public class TargetSerializerWithoutWordScores {
	public static void main(String[] args) {
		InitialTypeFactory factory = new InitialTypeFactory(new NameGenerator(Config.getSerializationVariablePrefix()));
		TargetSerializer loader = new TargetSerializer(factory, TargetConfig.getTarget());

		WordProcessor wordProcessor = new WordProcessor();	

		IWordExtractor extractor = new GroupWordExtractor(new DeclarationParserPipeline(new IDeclarationParser[]{new DeclarationParserOne(wordProcessor)}));

		loader.serialize(Config.getJarfolder(), Config.getStorageLocation(), extractor);
	}
}
