
public class Terminal extends Symbol {

	private String token;
	
	public Terminal(String token){
		this.token = token;
	}
	
	public String toString(){
		return token;
	}
	
}
