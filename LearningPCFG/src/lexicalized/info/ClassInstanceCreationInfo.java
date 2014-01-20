package lexicalized.info;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;

public class ClassInstanceCreationInfo extends LexicalizedInfo {

	private int argNum;
	private String targs;

	public ClassInstanceCreationInfo(String name, List<ASTNode> targs, int argNum) {
		super(name);
		
		this.targs = toTargs(targs);
		this.argNum = argNum;
		
		// TODO Auto-generated constructor stub
	}
	
	private String toTargs(List<ASTNode> targs){
		String s = "";
		
		if (targs.size() > 0){
		  s="<"+ targs.get(0).toString();
		  
		  int length = targs.size();
		  for (int i = 1; i < length;i++){
		    s += ","+targs.get(i).toString();
		  }
		
		  s+=">";
		}
		
		return s;
	}
	
	public String toNaiveString(){
		return super.toNaiveString()+","+this.targs+","+this.argNum;
	}	

}
