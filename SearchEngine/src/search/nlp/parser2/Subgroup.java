package search.nlp.parser2;

import java.util.List;

import search.WToken;

public class Subgroup {

	private List<WToken> wTokens;

	public Subgroup(List<WToken> wTokens) {
		this.wTokens = wTokens;
	}
	
	public List<WToken> getWTokens() {
		return this.wTokens;
	}
	
	public void setWTokens(List<WToken> wTokens) {
		this.wTokens = wTokens;
	}

	public boolean hasAnyWTokenWithSameLemmaAndPos(List<WToken> declWTokens) {
		for (WToken wToken : wTokens) {
			for (WToken declWToken : declWTokens) {
				if (wToken.equalsByPosAndLemma(declWToken)) return true;
			}
		}
		return false;
	}

	public boolean hasWTokenWithSameLemmaAndPos(WToken declWToken) {
		for (WToken wToken : wTokens) {
			if (wToken.equalsByPosAndLemma(declWToken)) return true;
		}
		return false;
	}
}
