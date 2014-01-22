package symbol;

public class VariableSymbol extends Symbol {

	private String token;
	
	public VariableSymbol(String token){
	  this.token = token;	
	}	

	public String toString(){
		return token;
	}
	
	public int hashCode(){
		return token.hashCode()^594867754;
	}
		
}
