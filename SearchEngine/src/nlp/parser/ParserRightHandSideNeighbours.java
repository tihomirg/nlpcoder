package nlp.parser;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ParserRightHandSideNeighbours implements IParser{

	private int numOfNonVerbs;

	public ParserRightHandSideNeighbours(int numOfNonVerbs) {
		this.numOfNonVerbs = numOfNonVerbs;
	}
	
	@Override
	public Input parse(Input input) {
		List<Sentence> sentences = input.getSentences();
		
		for (Sentence sentence : sentences) {
			Map<Integer, Group> groupMap = sentence.getGroupMap();
			
			List<Token> tokens = sentence.getTokens();
			
			for (Token token : tokens) {
				int index = token.getIndex();
				
				if(groupMap.containsKey(index)){
					Group group = groupMap.get(index);
				
					group.addRightHandSideNeighbours(rightNeighbours(index+1, sentence, groupMap, numOfNonVerbs));
					
				}
			}
			
		}
		
		return input;
	}

	private List<Group> rightNeighbours(int index, Sentence sentence, Map<Integer, Group> groupMap, int numOfNonVerbs) {
		LinkedList<Group> list = new LinkedList<Group>();
		
		if (index < sentence.size() && numOfNonVerbs > 0) {
			int num = numOfNonVerbs;
			if (groupMap.containsKey(index)){
				Group group = groupMap.get(index);
				Token token = group.getToken();
				
				if (!token.shouldSkipAsNeighbour()){
					list.add(groupMap.get(index));
					num--;
				}
			}
			list.addAll(rightNeighbours(index+1, sentence, groupMap, num));
		} 
		
		return list;
	}

}
