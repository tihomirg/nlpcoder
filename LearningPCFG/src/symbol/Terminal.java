package symbol;
import org.eclipse.jdt.core.dom.ASTNode;

public class Terminal extends Symbol {

	private String token;
	
	public Terminal(String token){
		assert token != null;
		this.token = token;
	}

	public String toString(){
		return token;
	}
	
	public int hashCode(){
		return token.hashCode()^348274982;
	}

}
