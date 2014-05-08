package deserializers;

import config.Config;
import api.InitialAPI;

public class Deserializer {

	public InitialAPI deserialize(String storageLocation) {
		KryoDeserializer deserializer = new KryoDeserializer();
		return (InitialAPI) deserializer.readObject(storageLocation, InitialAPI.class);
	}	
	
	public static void main(String[] args) {
		Deserializer deserializer = new Deserializer();
		
		InitialAPI cif = deserializer.deserialize(Config.getStorageLocation());
		System.out.println(cif.getClasses());
		
	}
}
