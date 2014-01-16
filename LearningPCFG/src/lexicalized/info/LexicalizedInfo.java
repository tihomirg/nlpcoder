package lexicalized.info;

public abstract class LexicalizedInfo {
	private String name;
	
	private boolean isUserDef;
	
	public LexicalizedInfo(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public boolean isUserDef() {
		return isUserDef;
	}

	public void setUserDef(boolean isUserDef) {
		this.isUserDef = isUserDef;
	}
}
