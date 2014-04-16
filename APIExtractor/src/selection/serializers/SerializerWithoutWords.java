package selection.serializers;

import selection.Config;
import selection.WordExtractor;
import selection.WordExtractorEmpty;
import selection.types.InitialTypeFactory;
import selection.types.NameGenerator;
import selection.types.TypeFactory;

public class SerializerWithoutWords {
	
	public static void main(String[] args) {
		InitialTypeFactory factory = new InitialTypeFactory(new NameGenerator(Config.getSerializationVariablePrefix()));
		Serializer serializer = new Serializer(factory);
		
		WordExtractor extractor = new WordExtractorEmpty();
		
		serializer.serialize(Config.getJarfolder(), Config.getStorageLocation(), extractor);	
	}
}
