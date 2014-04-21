package selection.serializers;

import definitions.factory.InitialClassInfoFactory;
import selection.Config;
import selection.WordExtractor;
import selection.WordExtractorEmpty;
import selection.loaders.BoundedClassInfoLoader;
import selection.types.InitialTypeFactory;
import selection.types.NameGenerator;

public class SerializerWithoutWords {
	
	public static void main(String[] args) {
		InitialTypeFactory factory = new InitialTypeFactory(new NameGenerator(Config.getSerializationVariablePrefix()));
		InitialClassInfoFactory cif = new InitialClassInfoFactory(factory);
		BoundedClassInfoLoader bcil = new BoundedClassInfoLoader(Config.getMaxFilesToScan(), cif);		
		Serializer serializer = new Serializer(bcil);
		
		WordExtractor extractor = new WordExtractorEmpty();
		
		serializer.serialize(Config.getJarfolder(), Config.getStorageLocation(), extractor);	
	}
}
