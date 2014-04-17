package selection.deserializers;

import java.util.Arrays;

import selection.Config;
import selection.types.NameGenerator;
import selection.types.StabileTypeFactory;
import definitions.ClassInfo;

public class Deserializer {
	
	private StabileTypeFactory factory;

	public Deserializer(StabileTypeFactory factory) {
		this.factory = factory;
	}

	public ClassInfo[] deserialize(String storageLocation) {
		TypeDeserializer serializer = new TypeDeserializer(factory);
		return (ClassInfo[]) serializer.readObject(storageLocation, ClassInfo[].class);
	}	
	
	public static void main(String[] args) {
		StabileTypeFactory factory = new StabileTypeFactory(new NameGenerator(Config.getDeserializerVariablePrefix()));
		Deserializer deserializer = new Deserializer(factory);
		
		System.out.println(Arrays.toString(deserializer.deserialize(Config.getStorageLocation())));
		
	}
}
