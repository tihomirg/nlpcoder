package selection.types;

public class NameGenerator {

	private String prefix; 
	private int count;
	
	public NameGenerator(String prefix) {
		this.prefix = prefix;
	}

	public String genNewName() {
		return prefix+(count++);
	}

}
