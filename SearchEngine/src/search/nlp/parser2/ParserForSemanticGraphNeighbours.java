package search.nlp.parser2;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.semgraph.SemanticGraph;

public class ParserForSemanticGraphNeighbours implements IParser {

	@Override
	public Input parse(Input curr) {
		List<Sentence> sentences = curr.getSentences();

		for (Sentence sentence : sentences) {
			SemanticGraph semanticGraph = sentence.getSemanticGraph();

			Map<Integer, RichToken> indexesToRichTokens = sentence.getIndexesToRichTokens();

			List<RichToken> richTokens = sentence.getRichTokens();

			for (RichToken richToken : richTokens) {
				IndexedWord node = semanticGraph.getNodeByIndexSafe(richToken.getIndex());
				if (node != null){
					List<IndexedWord> children = semanticGraph.getChildList(node);
					richToken.setSemanticNeighbours(transformToRichTokens(children, indexesToRichTokens));
				} else {
					richToken.setSemanticNeighbours(new LinkedList<RichToken>());
				}

			}

		}

		return curr;
	}

	private List<RichToken> transformToRichTokens(List<IndexedWord> children, Map<Integer, RichToken> indexesToRichTokens) {
		List<RichToken> richTokens = new LinkedList<RichToken>();

		for (IndexedWord child : children) {
			richTokens.add(indexesToRichTokens.get(child.index()));
		}

		return richTokens;
	}

}
