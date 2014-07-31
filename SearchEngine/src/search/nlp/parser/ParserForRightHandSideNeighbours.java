package search.nlp.parser;

import java.util.LinkedList;
import java.util.List;

public class ParserForRightHandSideNeighbours implements IParser {

	private int numOfGoodNeighbours;

	public ParserForRightHandSideNeighbours(int numOfGoodNeighbours) {
		this.numOfGoodNeighbours = numOfGoodNeighbours;
	}

	@Override
	public Input parse(Input curr) {
		List<Sentence> sentences = curr.getSentences();

		for (Sentence sentence : sentences) {
			List<RichToken> richTokens = sentence.getRichTokens();
			for (int i = 0; i < richTokens.size(); i++) {
				RichToken richToken = richTokens.get(i);
				List<RichToken> goods = getRightHandSideNeighbours(richTokens, i);
				
				richToken.setRightHandSideNeighbours(goods);
			}
		}

		return curr;
	}

	//We assume that richTokens are sorted based on indexes.
	//TODO: Check this.
	private List<RichToken> getRightHandSideNeighbours(List<RichToken> richTokens, int index) {
		List<RichToken> goods = new LinkedList<RichToken>();
		int goodCount = 0;
		for (int i = index+1; i < richTokens.size() && goodCount < numOfGoodNeighbours; i++) {
			RichToken richToken = richTokens.get(i);
			if (richToken.isGoodNeighbour()){
				goods.add(richToken);
				goodCount++;
			}
		}		

		return goods;
	}

}
