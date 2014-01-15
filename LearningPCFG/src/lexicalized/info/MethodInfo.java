package lexicalized.info;

public class MethodInfo extends LexicalizedInfo {

	private String name;
	private int argNum;

	public MethodInfo(String name, int argNum){
		this.name = name;
		this.argNum = argNum;
	}
	
	public String toString(){
		return "("+this.name+","+this.argNum+")";
	}
}
