package scopes;

import java.util.List;
import java.util.Stack;


public class SimpleEvalScopes extends ScopesKeyValue<String, List<String>>{
	
	private Stack<String> location;
	
	public SimpleEvalScopes() {
		this.location = new Stack<String>();
	//	this.location.push("");
	}
	
	public void popLocation(){
		this.location.pop();
	}
	
	public void pushLocation(String location){
		this.location.push(location);
	}
	
	public String locationToString(){
		return location.toString();
	}	
}
