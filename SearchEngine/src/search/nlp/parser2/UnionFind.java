package search.nlp.parser2;

import java.util.LinkedList;
import java.util.List;

import search.WToken;

public class UnionFind {

	private List<DisjointSubgroups> disjoints;

	public UnionFind() {
		this.disjoints = new LinkedList<DisjointSubgroups>();
	}

	public void addAll(List<Subgroup> subgroups) {
		for (Subgroup subgroup : subgroups) {
			add(subgroup);
		}
	}

	private void add(Subgroup subgroup) {
		List<DisjointSubgroups> containers = new LinkedList<DisjointSubgroups>();
		for (DisjointSubgroups disjoint : this.disjoints) {
			if(containsWTokenFromSubgroup(subgroup, disjoint)){
				containers.add(disjoint);
			}
		}

		DisjointSubgroups disjointSubgroups = new DisjointSubgroups();
		disjointSubgroups.addSubgroup(subgroup);

		if (containers.isEmpty()){
			this.disjoints.add(disjointSubgroups);	
		} else {
			this.disjoints.removeAll(containers);
			containers.add(disjointSubgroups);
			this.disjoints.add(merge(containers));
		}
	}

	private DisjointSubgroups merge(List<DisjointSubgroups> disjoints) {
		List<Subgroup> subgroups = new LinkedList<Subgroup>();
		for (DisjointSubgroups disjoint : disjoints) {
			subgroups.addAll(disjoint.getSubgroups());
		}

		return new DisjointSubgroups(subgroups);
	}

	private boolean containsWTokenFromSubgroup(Subgroup subgroup, DisjointSubgroups disjoint) {
		for (WToken wToken: subgroup.getWTokens()) {
			if(disjoint.hasWTokenWithSameLemmaAndPos(wToken)) return true;		
		}
		return false;
	}

	public List<DisjointSubgroups> getDisjoints() {
		return disjoints;
	}
}
