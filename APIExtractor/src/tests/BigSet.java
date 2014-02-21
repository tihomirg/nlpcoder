package tests;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.SynsetID;

public class BigSet {
	private Set<ISynsetID> synsets;

	public BigSet(Set<ISynsetID> synsets) {
		this.synsets = synsets;
	}	
	
	@Override
	public int hashCode() {
		int n = 17;
		int hash = 23;
		for(ISynsetID syn:synsets){
			hash += (int) (Math.pow(n, 2) + Math.pow(n+1, 2)) * syn.getOffset();
			n++;
		}
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BigSet other = (BigSet) obj;
		return this.hashCode() == other.hashCode();
	}

	public Set<ISynsetID> getSynsets() {
		return synsets;
	}
}
