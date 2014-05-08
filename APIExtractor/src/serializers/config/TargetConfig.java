package serializers.config;

public class TargetConfig {

	private static final String[] JAVA_LANG = new String[]{
		"java.lang", 
		"java.io",
		"java.util",
		"org.apache.commons.io",
		"org.apache.commons.io.comparator", 
		"org.apache.commons.io.filefilter",
		"org.apache.commons.io.input",
		"org.apache.commons.io.monitor", 
		"org.apache.commons.io.output"
	};

	public static String[] getTarget() {
		return JAVA_LANG;
	}

}
