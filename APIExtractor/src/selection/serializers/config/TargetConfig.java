package selection.serializers.config;

public class TargetConfig {

	private static final String[] JAVA_LANG = new String[]{"java.lang"};//, "java.io","java.util"};

	public static String[] getTarget() {
		return JAVA_LANG;
	}

}
