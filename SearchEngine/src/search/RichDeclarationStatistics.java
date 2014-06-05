package search;

import java.util.LinkedList;
import java.util.List;

import util.UtilList;
import nlp.parser.Token;
import definitions.Declaration;

public class RichDeclarationStatistics {
	private double declProb;

	private Slot[][] slotss;

	public RichDeclarationStatistics(Declaration decl, double declProb, int indexScoress[][]) {
		this.declProb = declProb;
		this.slotss = new Slot[2][];
		initiateGroups(decl, indexScoress);
	}

	private void initiateGroups(Declaration decl, int[][] indexScoress) {
		//First group
		createFirstGroup(decl.getSimpleNameTokens(), indexScoress[0]);
		
		//Second group
		createSecondGroup(decl.getReceiverTokens(), decl.getArgTokens(), decl.getClazzTokens(), decl.getAdditionalReceiverTokens(), decl.getReturnTypeTokens(), indexScoress[1]);
	}

	private void createFirstGroup(List<Token> simpleNameTokens, int[] indexScores) {
		List<Slot> slots = new LinkedList<Slot>();
		for (Token token : simpleNameTokens) {
			Slot slot = new Slot(indexScores);
			slot.addToken(token);
			slots.add(slot);
		}
		
		slotss[0] = slots.toArray(new Slot[slots.size()]);
	}
	
	private void createSecondGroup(List<Token> receiverTokens, List<List<Token>> argTokens, List<Token> clazzTokens, List<List<Token>> additionalReceiverTokens, List<Token> returnTypeTokens, int[] indexScores) {
		List<List<Token>> tokenss = new LinkedList<List<Token>>();
		
		List<List<Token>> receiverTokenss = new LinkedList<List<Token>>();
		receiverTokenss.add(receiverTokens);
		receiverTokenss.addAll(additionalReceiverTokens);
		
		tokenss.addAll(argTokens);
		tokenss.add(UtilList.flatten(receiverTokenss));
		tokenss.add(clazzTokens);
		tokenss.add(returnTypeTokens);
		
		List<Slot> slots = new LinkedList<Slot>();
		for (List<Token> tokens : tokenss) {
			Slot slot = new Slot(indexScores);
			for (Token token : tokens) {
				slot.addToken(token);
			}
			slots.add(slot);			
		}
		
		slotss[1] = slots.toArray(new Slot[slots.size()]);		
	}	

	public void hit(WToken wtoken) {
		fit(wtoken);
	}

	private void fit(WToken wtoken) {
		WToken current = wtoken;
		int currentIndex = wtoken.getIndex();

		while(true){
			Slot[] slots = slotss[currentIndex];

			for (int i = 0; i < slots.length; i++) {
				Slot slot = slots[i];
				if (slot.fits(current)){
					if (slot.isOccupied()){
						current = slot.substitute(current);
					} else {
						slot.setWToken(current);
						return;
					}
				}
			}

			//if these are the same we need to check different group
			if (currentIndex == current.getIndex()){
				currentIndex = (currentIndex + 1) % 2;
			} else return;
		}
	}

	public void clear() {
		for (Slot[] slots : this.slotss) {
			for (Slot slot : slots) {
				slot.clear();
			}
		}
	}

	public double getDeclProb() {
		return declProb;
	}

	public Slot[][] getSlots() {
		return this.slotss;
	}
}
