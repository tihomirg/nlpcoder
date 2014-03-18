package simulations;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import declarations.API;
import declarations.Imported;
import definitions.ClassInfo;
import definitions.Declaration;
import selection.Config;
import selection.deserializers.FullDeserializer;
import selection.types.NameGenerator;
import selection.types.TypeFactory;

public class TestDefaultImport {
	public static void main(String[] args) {
		TypeFactory factory = new TypeFactory(new NameGenerator(Config.getDeserializerVariablePrefix()));
		FullDeserializer deserializer = new FullDeserializer(factory);
		ClassInfo[] classes = deserializer.deserialize(Config.getStorageLocation());
		API api = new API();
		api.addClasses(classes);
		
		Imported java = api.createImported();
		api.load(java, "java.util", false);
		
		System.out.println(Arrays.toString(((ClassInfo)(java.getClasses("Collection").toArray()[0])).getUniqueDeclarations()));
		
		//System.out.println(java.getMethods("append"));
	}
}
