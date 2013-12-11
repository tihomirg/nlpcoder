package symbol;
import org.eclipse.jdt.core.dom.ASTNode;

public class Terminal extends Symbol {

	private String token;
	private ASTNode parent;
	
	public Terminal(String token){
		this.token = token;
	}
	
	public Terminal(String token, ASTNode parent){
		this.token = token;
		this.parent = parent;
	}

	@Override
	protected ASTNode getParent() {
		return parent;
	}

	@Override
	protected String naive() {
		return token;
	}
	
}
