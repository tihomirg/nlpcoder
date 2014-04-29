package simulations;
import java.util.Arrays;
import api.Imported;
import api.StabileAPI;
import definitions.ClassInfo;
import selection.Config;
import selection.deserializers.Deserializer;
import selection.types.NameGenerator;

public class TestDefaultImport {
	public static void main(String[] args) {
		Deserializer deserializer = new Deserializer();
		StabileAPI api = new StabileAPI( deserializer.deserialize(Config.getStorageLocation()), new NameGenerator(Config.getDeserializerVariablePrefix()));
		
		Imported java = api.createImported();
		api.load(java, "java.util.List", true);
		
		System.out.println(Arrays.toString(((ClassInfo)(java.getClasses("Collection").toArray()[0])).getUniqueDeclarations()));
		
		//System.out.println(java.getMethods("append"));
	}
}
