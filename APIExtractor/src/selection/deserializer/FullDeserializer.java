package selection.deserializer;

import java.util.Arrays;

import selection.Config;
import selection.DataSerializer;
import selection.serializers.TypeSerializer;
import selection.types.NameGenerator;
import selection.types.TypeFactory;
import definitions.ClassInfo;

public class FullDeserializer {
	
	private TypeFactory factory;

	public FullDeserializer(TypeFactory factory) {
		this.factory = factory;
	}

	public ClassInfo[] deserialize(String storageLocation) {
		TypeSerializer serializer = new TypeSerializer(factory);
		return (ClassInfo[]) serializer.readObject(storageLocation, ClassInfo[].class);
	}	
	
	public static void main(String[] args) {
		TypeFactory factory = new TypeFactory(new NameGenerator("DV"));
		FullDeserializer deserializer = new FullDeserializer(factory);
		
		System.out.println(Arrays.toString(deserializer.deserialize(Config.getStorageLocation())));
		
	}
}
