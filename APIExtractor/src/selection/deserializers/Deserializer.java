package selection.deserializers;

import selection.Config;
import selection.types.NameGenerator;
import selection.types.StabileTypeFactory;
import definitions.factory.InitialClassInfoFactory;

public class Deserializer {
	
	private StabileTypeFactory factory;

	public Deserializer(StabileTypeFactory factory) {
		this.factory = factory;
	}

	public InitialClassInfoFactory deserialize(String storageLocation) {
		KryoDeserializer deserializer = new KryoDeserializer(factory);
		return (InitialClassInfoFactory) deserializer.readObject(storageLocation, InitialClassInfoFactory.class);
	}	
	
	public static void main(String[] args) {
		StabileTypeFactory factory = new StabileTypeFactory(new NameGenerator(Config.getDeserializerVariablePrefix()));
		Deserializer deserializer = new Deserializer(factory);
		
		InitialClassInfoFactory cif = deserializer.deserialize(Config.getStorageLocation());
		System.out.println(cif.getClasses());
		
	}
}
