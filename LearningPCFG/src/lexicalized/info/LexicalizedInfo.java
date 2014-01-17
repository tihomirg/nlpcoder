package lexicalized.info;

public abstract class LexicalizedInfo {
	protected String name;
	
	protected boolean isUserDef;
	
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
	
	protected String toNaiveString(){
		return (this.isUserDef? "User,":"API,")+this.name;
	}
	
	public String toString(){
		return "("+toNaiveString()+")";
	}	
}
