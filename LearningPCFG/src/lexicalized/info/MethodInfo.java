package lexicalized.info;

public class MethodInfo extends LexicalizedInfo {

	private int argNum;

	public MethodInfo(String name, int argNum){
		super(name);
		this.argNum = argNum;
	}
	
	public String toNaiveString(){
		return super.toNaiveString()+","+this.argNum;
	}
}
