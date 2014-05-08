package serializers;

import nlp.extractors.GroupWordExtractor;
import nlp.extractors.WordExtractor;
import nlp.parser.declarations.DeclarationParserOne;
import nlp.parser.declarations.DeclarationParserPipeline;
import nlp.parser.declarations.IDeclarationParser;
import nlp.processors.WordProcessor;
import config.Config;
import loaders.ClassInfoLoader;
import loaders.TargetJarLoader;
import api.InitialAPI;
import serializers.config.TargetConfig;
import types.InitialTypeFactory;
import types.NameGenerator;

public class TargetSerializer {
	
	public static void main(String[] args) {
		InitialTypeFactory factory = new InitialTypeFactory(new NameGenerator(Config.getSerializationVariablePrefix()));
		InitialAPI cif = new InitialAPI(factory);
		ClassInfoLoader cil = new TargetJarLoader(Config.getMaxFilesToScan(), TargetConfig.getTarget(), cif);
		Serializer loader = new Serializer(cil);
		
		WordProcessor wordProcessor = new WordProcessor();
		WordExtractor extractor = new GroupWordExtractor(new DeclarationParserPipeline(new IDeclarationParser[]{new DeclarationParserOne(wordProcessor)}));
		
		loader.serialize(Config.getJarfolder(), Config.getStorageLocation(), extractor);
	
	}	
}
