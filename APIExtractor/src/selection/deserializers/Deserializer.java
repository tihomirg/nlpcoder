package selection.deserializers;

import selection.Config;
import definitions.factory.InitialClassInfoFactory;

public class Deserializer {

	public InitialClassInfoFactory deserialize(String storageLocation) {
		KryoDeserializer deserializer = new KryoDeserializer();
		return (InitialClassInfoFactory) deserializer.readObject(storageLocation, InitialClassInfoFactory.class);
	}	
	
	public static void main(String[] args) {
		Deserializer deserializer = new Deserializer();
		
		InitialClassInfoFactory cif = deserializer.deserialize(Config.getStorageLocation());
		System.out.println(cif.getClasses());
		
	}
}
