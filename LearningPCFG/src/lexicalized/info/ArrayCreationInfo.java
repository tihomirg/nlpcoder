package lexicalized.info;

public class ArrayCreationInfo extends LexicalizedInfo {

	private int dim;

	public ArrayCreationInfo(String name, int dim) {
		super(name);
		this.dim = dim;
		// TODO Auto-generated constructor stub
	}
	
	public String toNaiveString(){
		return super.toNaiveString()+","+this.dim;
	}

}
