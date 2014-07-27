package search.nlp.parser2;

import java.util.List;

import search.WToken;

public class DisjointSubgroups {

	private List<Subgroup> subroups;
	
	public List<Subgroup> getSubgroups() {
		return subroups;
	}

	public boolean hasWTokenWithSameLemmaAndPos(WToken wToken) {
		for (Subgroup subgroup : subroups) {
			if(subgroup.hasWTokenWithSameLemmaAndPos(wToken)) return true;
		}
		return false;
	}

}
