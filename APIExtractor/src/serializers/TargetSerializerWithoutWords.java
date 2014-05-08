package serializers;

import nlp.extractors.WordExtractor;
import nlp.extractors.WordExtractorEmpty;
import config.Config;
import loaders.ClassInfoLoader;
import loaders.TargetJarLoader;
import api.InitialAPI;
import serializers.config.TargetConfig;
import types.InitialTypeFactory;
import types.NameGenerator;

public class TargetSerializerWithoutWords {
	public static void main(String[] args) {
		InitialTypeFactory factory = new InitialTypeFactory(new NameGenerator(Config.getSerializationVariablePrefix()));
		InitialAPI cif = new InitialAPI(factory);
		ClassInfoLoader cil =  new TargetJarLoader(Config.getMaxFilesToScan(), TargetConfig.getTarget(), cif);
		Serializer loader = new Serializer(cil);
		
		WordExtractor extractor = new WordExtractorEmpty();
		
		loader.serialize(Config.getJarfolder(), Config.getStorageLocation(), extractor);
	
	}
}
