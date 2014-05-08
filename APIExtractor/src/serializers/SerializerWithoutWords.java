package serializers;

import nlp.extractors.WordExtractor;
import nlp.extractors.WordExtractorEmpty;
import config.Config;
import loaders.BoundedClassInfoLoader;
import api.InitialAPI;
import types.InitialTypeFactory;
import types.NameGenerator;

public class SerializerWithoutWords {
	
	public static void main(String[] args) {
		InitialTypeFactory factory = new InitialTypeFactory(new NameGenerator(Config.getSerializationVariablePrefix()));
		InitialAPI cif = new InitialAPI(factory);
		BoundedClassInfoLoader bcil = new BoundedClassInfoLoader(Config.getMaxFilesToScan(), cif);		
		Serializer serializer = new Serializer(bcil);
		
		WordExtractor extractor = new WordExtractorEmpty();
		
		serializer.serialize(Config.getJarfolder(), Config.getStorageLocation(), extractor);	
	}
}
