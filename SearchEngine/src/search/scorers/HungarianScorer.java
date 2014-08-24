package search.scorers;

import java.util.LinkedList;
import java.util.List;

import search.DeclarationSelectionEntry;
import search.WToken;
import search.config.SearchConfig;
import search.nlp.parser.DisjointSubgroups;
import search.nlp.parser.RichToken;
import search.nlp.parser.Subgroup;
import util.Pair;

public class HungarianScorer implements RichDeclarationScorer {
	
	@Override
	public double calculate(DeclarationSelectionEntry rd, RichToken richToken) {
		List<Bigraph> bigraphs = new LinkedList<Bigraph>();
		List<WToken> allDeclWTokens = rd.getWTokens();
		
		
		List<DisjointSubgroups> disjointSubgroupss = richToken.getDisjointSubgroups();
		for (DisjointSubgroups disjointSubgroups : disjointSubgroupss) {
			List<WToken> declWTokens = filterDeclWTokens(disjointSubgroups, allDeclWTokens);
			if (!declWTokens.isEmpty()){
				List<Subgroup> allSubgroups = disjointSubgroups.getSubgroups();
				List<Subgroup> subgroups = filterSubgorups(allSubgroups, declWTokens);
				if (!subgroups.isEmpty()){
					bigraphs.add(new Bigraph(subgroups, declWTokens, SearchConfig.getDeclarationInputKindMatrix()));
				}
			}
		}
		
		Pair<Double, Integer> result = calculateMaxMatchingScore(bigraphs);
		return result.getFirst() - calculateUnmatchedPenalty(rd, richToken, result.getSecond());
	}

	private double calculateUnmatchedPenalty(DeclarationSelectionEntry rd, RichToken richToken, int numOfMatchings) {
		int maxNumOfMatchings = Math.min(rd.getNumberOfWTokensWithoutAdditionalReceiverTokens(), richToken.getAllTokens().size());
		int numOfUnmatchings = Math.max(0, maxNumOfMatchings - numOfMatchings); 
		return SearchConfig.getDeclarationInputUnmatchingWeight() * numOfUnmatchings;
	}

	private Pair<Double, Integer> calculateMaxMatchingScore(List<Bigraph> bigraphs) {
		double score = 0;
		int numOfMatchings = 0;
		for (Bigraph bigraph : bigraphs) {
			Pair<Double, Integer> result= bigraph.calculate();
			score += result.getFirst();
			numOfMatchings += result.getSecond();
		}
		return new Pair<Double, Integer>(score, numOfMatchings);
	}

	private List<Subgroup> filterSubgorups(List<Subgroup> subgroups, List<WToken> declWTokens) {
		List<Subgroup> filtered = new LinkedList<Subgroup>();
		for (Subgroup subgroup: subgroups) {
			if (subgroup.hasAnyWTokenWithSameLemmaAndPos(declWTokens)) filtered.add(subgroup);
		}
		return filtered;
	}

	private List<WToken> filterDeclWTokens(DisjointSubgroups disjointSubgroups, List<WToken> declWTokens) {
		List<WToken> filtered = new LinkedList<WToken>();
		for (WToken wToken : declWTokens) {
			if(disjointSubgroups.hasWTokenWithSameLemmaAndPos(wToken)){
				filtered.add(wToken);
			}
		}
		return filtered;
	}

}
