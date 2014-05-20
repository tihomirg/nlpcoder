package deserializers;

import java.util.Collection;

import config.Config;
import definitions.ClassInfo;
import api.InitialAPI;

public class Deserializer {

	public InitialAPI deserialize(String storageLocation) {
		KryoDeserializer deserializer = new KryoDeserializer();
		return (InitialAPI) deserializer.readObject(storageLocation, InitialAPI.class);
	}	
	
	public static void main(String[] args) {
		Deserializer deserializer = new Deserializer();
		
		InitialAPI cif = deserializer.deserialize(Config.getStorageLocation());
		Collection<ClassInfo> classes = cif.getClasses();
		System.out.println(classes);
		
		System.out.println("Total "+classes.size()+" classes.");
		
	}
}
