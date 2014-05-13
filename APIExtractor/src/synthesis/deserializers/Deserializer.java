package synthesis.deserializers;

import config.Config;
import deserializers.KryoDeserializer;
import api.InitialAPI;
import api.StabileAPI;

public class Deserializer {

	public StabileAPI deserialize(String storageLocation) {
		KryoDeserializer deserializer = new KryoDeserializer();
		return (StabileAPI) deserializer.readObject(storageLocation, InitialAPI.class);
	}	
	
	public static void main(String[] args) {
		Deserializer deserializer = new Deserializer();
		
		StabileAPI cif = deserializer.deserialize(Config.getStorageLocation());
		
	}
}
