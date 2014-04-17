package simulations;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import declarations.API;
import declarations.Imported;
import definitions.ClassInfo;
import definitions.Declaration;
import selection.Config;
import selection.deserializers.Deserializer;
import selection.types.NameGenerator;
import selection.types.StabileTypeFactory;
import selection.types.TypeFactory;

public class TestDefaultImport {
	public static void main(String[] args) {
		StabileTypeFactory factory = new StabileTypeFactory(new NameGenerator(Config.getDeserializerVariablePrefix()));
		Deserializer deserializer = new Deserializer(factory);
		ClassInfo[] classes = deserializer.deserialize(Config.getStorageLocation());
		API api = new API(factory);
		api.addClasses(classes);
		
		Imported java = api.createImported();
		api.load(java, "java.util.List", true);
		
		System.out.println(Arrays.toString(((ClassInfo)(java.getClasses("Collection").toArray()[0])).getUniqueDeclarations()));
		
		//System.out.println(java.getMethods("append"));
	}
}
