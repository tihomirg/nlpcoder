package selection;

import java.util.List;

import nlp.parser.Token;

public class Hit {
	private List<Token> hitTokens;
	private WToken hitByWToken;
	
	public Hit(WToken hitByWToken, List<Token> hitTokens) {
		this.hitTokens = hitTokens;
		this.hitByWToken = hitByWToken;
	}

	public List<Token> getHitToken() {
		return hitTokens;
	}

	public void setHitToken(List<Token> hitTokens) {
		this.hitTokens = hitTokens;
	}

	public WToken getHitByWToken() {
		return hitByWToken;
	}

	public void setHitByWToken(WToken hitByWToken) {
		this.hitByWToken = hitByWToken;
	}
}
