package selection.serializers;

import selection.Config;
import selection.IWordExtractor;
import selection.WordExtractorEmpty;
import selection.types.NameGenerator;
import selection.types.TypeFactory;

public class SerializerWithoutWords {
	
	public static void main(String[] args) {
		TypeFactory factory = new TypeFactory(new NameGenerator(Config.getSerializationVariablePrefix()));
		Serializer serializer = new Serializer(factory);
		
		IWordExtractor extractor = new WordExtractorEmpty();
		
		serializer.serialize(Config.getJarfolder(), Config.getStorageLocation(), extractor);	
	}
}
