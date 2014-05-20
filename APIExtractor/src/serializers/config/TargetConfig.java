package serializers.config;

public class TargetConfig {

	private static final String[] JAVA_LANG = new String[]{
		"java.lang", 
		"java.io",
		"java.util",
		"java.net",
		"org.apache.commons.io",
		"org.apache.commons.io.comparator", 
		"org.apache.commons.io.filefilter",
		"org.apache.commons.io.input",
		"org.apache.commons.io.monitor", 
		"org.apache.commons.io.output",
		
		//commons.lang
		//also there exists commons.lang3
		"org.apache.commons.lang", 
		"org.apache.commons.lang.builder", 
		"org.apache.commons.lang.enum", 
		"org.apache.commons.lang.enums", 
		"org.apache.commons.lang.exception", 
		"org.apache.commons.lang.math", 
		"org.apache.commons.lang.mutable", 
		"org.apache.commons.lang.reflect", 
		"org.apache.commons.lang.text", 
		"org.apache.commons.lang.time",
		
		"org.apache.commons.collections", 
		"org.apache.commons.collections.bag", 
		"org.apache.commons.collections.bidimap", 
		"org.apache.commons.collections.buffer", 
		"org.apache.commons.collections.collection", 
		"org.apache.commons.collections.comparators", 
		"org.apache.commons.collections.functors", 
		"org.apache.commons.collections.iterators", 
		"org.apache.commons.collections.keyvalue", 
		"org.apache.commons.collections.list", 
		"org.apache.commons.collections.map", 
		"org.apache.commons.collections.set" 
		
	};

	public static String[] getTarget() {
		return JAVA_LANG;
	}

}
