package selection.serializers;

import selection.Config;
import selection.IWordExtractor;
import selection.WordExtractorEmpty;
import selection.types.TypeFactory;

public class FullSerializerWithoutWords {
	
	public static void main(String[] args) {
		TypeFactory factory = new TypeFactory();
		FullSerializer serializer = new FullSerializer(factory);
		
		IWordExtractor extractor = new WordExtractorEmpty();
		
		serializer.serialize(Config.getJarfolder(), Config.getStorageLocation(), extractor);	
	}
}
