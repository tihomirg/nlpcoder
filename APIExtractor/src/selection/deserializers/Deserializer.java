package selection.deserializers;

import java.util.Arrays;

import selection.Config;
import selection.DataSerializer;
import selection.types.NameGenerator;
import selection.types.StabileTypeFactory;
import selection.types.TypeFactory;
import tests.TypeSerializer;
import definitions.ClassInfo;

public class Deserializer {
	
	private TypeFactory factory;

	public Deserializer(TypeFactory factory) {
		this.factory = factory;
	}

	public ClassInfo[] deserialize(String storageLocation) {
		TypeSerializer serializer = new TypeSerializer(factory);
		return (ClassInfo[]) serializer.readObject(storageLocation, ClassInfo[].class);
	}	
	
	public static void main(String[] args) {
		TypeFactory factory = new StabileTypeFactory(new NameGenerator(Config.getDeserializerVariablePrefix()));
		Deserializer deserializer = new Deserializer(factory);
		
		System.out.println(Arrays.toString(deserializer.deserialize(Config.getStorageLocation())));
		
	}
}
