package simulations;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import declarations.API;
import declarations.Imported;
import definitions.ClassInfo;
import definitions.Declaration;
import selection.Config;
import selection.loaders.DeclarationDeserializer;

public class TestDefaultImport {
	public static void main(String[] args) {
		DeclarationDeserializer deserializer = new DeclarationDeserializer();
		ClassInfo[] classes = deserializer.deserialize(Config.getStorageLocation());
		API api = new API();
		api.addClasses(classes);
		
		Imported java = api.createImported();
		
		System.out.println(Arrays.toString(((ClassInfo)(java.getClasses("Integer").toArray()[0])).getUniqueDeclarations()));
		
		//System.out.println(java.getMethods("append"));
	}
}
