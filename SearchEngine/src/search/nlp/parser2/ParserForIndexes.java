package search.nlp.parser2;

import java.util.LinkedList;
import java.util.List;

import search.WToken;
import util.UtilList;

public class ParserForIndexes implements IParser {

	@Override
	public Input parse(Input input) {
		for (Sentence sentence : input.getSentences()) {
			for (RichToken richToken : sentence.getRichTokens()) {
				List<List<WToken>> relatedWTokens = richToken.getRelatedWTokens();
				List<WToken> leadingWTokens = richToken.getLeadingWTokens();
				List<WToken> secondaryWTokens = richToken.getSecondaryWTokens();
				
				List<WToken> primaryWTokens = new LinkedList<WToken>();				
				primaryWTokens.addAll(leadingWTokens);
				primaryWTokens.addAll(UtilList.flatten(relatedWTokens));
				
				setImportanceIndex(primaryWTokens, 0);
				setImportanceIndex(secondaryWTokens, 1);
				
				//Subgroup index
				setSubgroupIndexess(merge(mergePrimaryWTokens(leadingWTokens, relatedWTokens), secondaryWTokens));
			}
		}

		return input;
	}

	private void setSubgroupIndexess(List<List<WToken>> wTokenSubgroups) {
		int size = wTokenSubgroups.size();
		
		for (int i = 0; i < size; i++) {
			setSubgroupIndexes(wTokenSubgroups.get(i), i);
		}
		
	}

	private void setSubgroupIndexes(List<WToken> list, int index) {
		for (WToken wToken : list) {
			wToken.setSubgroupIndex(index);
		}
	}

	private List<List<WToken>> merge(List<List<WToken>> primaryWTokenss, List<WToken> secondaryWTokens) {
		List<List<WToken>> secondaryWTokenss = UtilList.mapToSingletons(secondaryWTokens);
		return UtilList.merge(primaryWTokenss, secondaryWTokenss);
	}

	private List<List<WToken>> mergePrimaryWTokens(List<WToken> leadingWTokens, List<List<WToken>> relatedWTokens) {
		List<List<WToken>> wTokenss = new LinkedList<List<WToken>>();
		
		int size = leadingWTokens.size();
		for (int i = 0; i < size; i++) {
			List<WToken> wTokens = new LinkedList<WToken>();
			wTokens.add(leadingWTokens.get(i));
			wTokens.addAll(relatedWTokens.get(i));
			wTokenss.add(wTokens);
		}
		
		return wTokenss;
	}

	private void setImportanceIndex(List<WToken> wTokens, int index) {
		for (WToken wToken : wTokens) {
			wToken.setImportanceIndex(index);
		}
	}
}
 