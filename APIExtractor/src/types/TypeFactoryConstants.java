package types;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TypeFactoryConstants {

	public static final Set<String> primitiveNames = new HashSet<String>(Arrays.asList(new String[]{"byte", "short", "int", "long", "float", "double", "boolean","char"}));

	public static final Set<String> boxedNames = 
		new HashSet<String>(Arrays.asList(new String[]{
			java.lang.Byte.class.getName(), 
			java.lang.Short.class.getName(),
			java.lang.Integer.class.getName(),
			java.lang.Long.class.getName(),
			java.lang.Float.class.getName(),
			java.lang.Double.class.getName(),
			java.lang.Boolean.class.getName(),
			java.lang.Character.class.getName()}));;
			
   public static final Map<String, String> primitiveToBoxed = new HashMap<String, String>(){
		{
			put("byte", java.lang.Byte.class.getName());
			put("short", java.lang.Short.class.getName());
			put("int", java.lang.Integer.class.getName());
			put("long", java.lang.Long.class.getName());
			put("float", java.lang.Float.class.getName());
			put("double", java.lang.Double.class.getName());
			put("boolean", java.lang.Boolean.class.getName());
			put("char", java.lang.Character.class.getName());
		
		}
	};

	public static Map<String, String> boxedToPrimitive = new HashMap<String, String>(){
		{
			put(java.lang.Byte.class.getName(), "byte");
			put(java.lang.Short.class.getName(), "short");
			put(java.lang.Integer.class.getName(), "int");
			put(java.lang.Long.class.getName(), "long");
			put(java.lang.Float.class.getName(), "float");
			put(java.lang.Double.class.getName(), "double");
			put(java.lang.Boolean.class.getName(), "boolean");
			put(java.lang.Character.class.getName(), "char");
		
		}
	};	
}
