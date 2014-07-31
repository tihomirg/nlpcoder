package search.nlp.parser;

import java.util.LinkedList;
import java.util.List;

import search.WToken;

public class DisjointSubgroups {

	private List<Subgroup> subroups;
	
	public DisjointSubgroups() {
		this(new LinkedList<Subgroup>());
	}
	
	public DisjointSubgroups(List<Subgroup> subgroups) {
		this.subroups = subgroups;
	}	
	
	public List<Subgroup> getSubgroups() {
		return subroups;
	}
	
	public void addSubgroup(Subgroup subgroup){
		this.subroups.add(subgroup);
	}

	public boolean hasWTokenWithSameLemmaAndPos(WToken wToken) {
		for (Subgroup subgroup : subroups) {
			if(subgroup.hasWTokenWithSameLemmaAndPos(wToken)) return true;
		}
		return false;
	}

}
