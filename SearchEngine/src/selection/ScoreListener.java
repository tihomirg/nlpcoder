package selection;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import selection.comparators.RichDeclarationComparatorAsce;
import selection.comparators.RichDeclarationComparatorDesc;

public class ScoreListener {

	private static final RichDeclarationComparatorAsce comparatorAsce = new RichDeclarationComparatorAsce();
	private static final RichDeclarationComparatorDesc comparatorDesc = new RichDeclarationComparatorDesc();

	private List<RichDeclaration> changed;

	private PriorityQueue<RichDeclaration> worstRDs;
	private PriorityQueue<RichDeclaration> bestRDs;
	
	private int maxSize;
	private int currentSize;
	
	public ScoreListener(int maxSize) {
		this.changed = new LinkedList<RichDeclaration>();
		this.maxSize = maxSize;
		this.worstRDs = new PriorityQueue<RichDeclaration>(maxSize+1, comparatorAsce);
		this.bestRDs = new PriorityQueue<RichDeclaration>(maxSize+1, comparatorDesc);
	}
	
	public void notify(RichDeclaration rd) {
		add(rd);
		keepMaxSize();
		
		changed(rd);
	}

	private void keepMaxSize() {
		if(this.currentSize > maxSize){
			this.bestRDs.remove(this.worstRDs.remove());
			this.currentSize--;
		}
	}

	private void add(RichDeclaration rd) {
		this.bestRDs.add(rd);
		this.worstRDs.add(rd);
		this.currentSize++;
	}

	private void changed(RichDeclaration rd) {
		if(!rd.isHit()){
			changed.add(rd);
			rd.setHit(true);
		}
	}
	
	private void clear(){
		for (RichDeclaration rd : changed) {
			rd.clear();
		}
		changed.clear();
		worstRDs.clear();
		bestRDs.clear();
	}

}
