package selection.loaders;

import java.util.Arrays;

import selection.Config;
import selection.DataSerializer;
import definitions.ClassInfo;

public class DeclarationDeserializer {
	
	public ClassInfo[] deserialize(String storageLocation) {
		DataSerializer serializer = new DataSerializer();
		return (ClassInfo[]) serializer.readObject(storageLocation, ClassInfo[].class);
	}	
	
	public static void main(String[] args) {
		DeclarationDeserializer loader = new DeclarationDeserializer();
		
		
		System.out.println(Arrays.toString(loader.deserialize(Config.getStorageLocation())));
		
		
	}
}
