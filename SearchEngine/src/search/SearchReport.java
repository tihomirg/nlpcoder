package search;

import java.util.List;

import search.nlp.parser.RichToken;

public class SearchReport {

	private List<RichDeclaration> results;
	private RichToken searchKey;

	public SearchReport(List<RichDeclaration> results, RichToken searchKey) {
		this.results = results;
		this.searchKey = searchKey;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb.append(searchKey.tokensToString() +"\n\n");
		for (RichDeclaration richDeclaration : results) {
			sb.append(richDeclaration+"\n");
		}
		sb.append("\n");
		return sb.toString();
	}

	public List<RichDeclaration> getResults() {
		return results;
	}

}
