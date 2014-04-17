package selection.deserializers;

import java.util.Arrays;

import selection.Config;
import selection.types.NameGenerator;
import selection.types.StabileTypeFactory;
import definitions.ClassInfo;
import definitions.StabileClassInfoFactory;

public class Deserializer {
	
	private StabileTypeFactory factory;
	private StabileClassInfoFactory cif;

	public Deserializer(StabileTypeFactory factory, StabileClassInfoFactory cif) {
		this.factory = factory;
		this.cif = cif;
	}

	public ClassInfo[] deserialize(String storageLocation) {
		KryoDeserializer serializer = new KryoDeserializer(factory, cif);
		return (ClassInfo[]) serializer.readObject(storageLocation, ClassInfo[].class);
	}	
	
	public static void main(String[] args) {
		StabileTypeFactory factory = new StabileTypeFactory(new NameGenerator(Config.getDeserializerVariablePrefix()));
		StabileClassInfoFactory classInfoFactory = new StabileClassInfoFactory();
		Deserializer deserializer = new Deserializer(factory, classInfoFactory);
		
		System.out.println(Arrays.toString(deserializer.deserialize(Config.getStorageLocation())));
		
	}
}
