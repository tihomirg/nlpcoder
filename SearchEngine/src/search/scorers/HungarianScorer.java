package search.scorers;

import java.util.LinkedList;
import java.util.List;
import search.DeclarationSelectionEntry;
import search.WToken;
import search.nlp.parser.DisjointSubgroups;
import search.nlp.parser.RichToken;
import search.nlp.parser.Subgroup;

public class HungarianScorer implements RichDeclarationScorer {

	private double[][] kindMatrix;

	public HungarianScorer(double[][] kindMatrix) {
		this.kindMatrix = kindMatrix;
	}

	@Override
	public double calculate(DeclarationSelectionEntry rd, RichToken richToken) {
		List<Bigraph> bigraphs = new LinkedList<Bigraph>();
		List<WToken> allDeclWTokens = rd.getWTokens();
		for (DisjointSubgroups disjointSubgroups : richToken.getDisjointSubgroups()) {
			List<WToken> declWTokens = filterDeclWTokens(disjointSubgroups, allDeclWTokens);
			if (!declWTokens.isEmpty()){
				List<Subgroup> allSubgroups = disjointSubgroups.getSubgroups();
				List<Subgroup> subgroups = filterSubgorups(allSubgroups, declWTokens);
				if (!subgroups.isEmpty()){
					bigraphs.add(new Bigraph(subgroups, declWTokens, kindMatrix));
				}
			}
		}

		return calculate(bigraphs);
	}

	private double calculate(List<Bigraph> bigraphs) {
		double score = 0;
		for (Bigraph bigraph : bigraphs) {
			score += bigraph.calculate();
		}
		return score;
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
