package selection.types;

public class NameGenerator {

	private String prefix; 
	private int count;
	
	public NameGenerator() {}	
	
	public NameGenerator(String prefix) {
		this.prefix = prefix;
	}

	public String genNewName() {
		return prefix+(count++);
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
