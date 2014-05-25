package synthesis;

import statistics.posttrees.Expr;
import synthesis.handlers.SearchKey;

public class Param {
	private SearchKey searchKey;
	private RepKey repKey;
	
	public Param(SearchKey searchKey, RepKey repKey) {
		this.searchKey = searchKey;
		this.repKey = repKey;
	}	
	
	public Param(Expr expr, int id) {
		this.searchKey = new SearchKey(expr);
		this.repKey = new RepKey(id);
	}

	public SearchKey getSearchKey() {
		return searchKey;
	}
	
	public void setSearchKey(SearchKey searchKey) {
		this.searchKey = searchKey;
	}
	
	public RepKey getRepKey() {
		return repKey;
	}
	
	public void setRepKey(RepKey repKey) {
		this.repKey = repKey;
	}
	
	@Override
	public String toString() {
		return searchKey.toString();
	}
}