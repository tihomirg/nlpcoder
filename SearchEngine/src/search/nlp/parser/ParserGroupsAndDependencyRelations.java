package search.nlp.parser;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import search.nlp.parser.IParser;
import nlp.parser.Token;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.semgraph.SemanticGraph;

public class ParserGroupsAndDependencyRelations implements IParser {

	@Override
	public Input parse(Input input) {
		List<Sentence> sentences = input.getSentences();
		
		for (Sentence sentence : sentences) {
			Map<Integer, Group> groups = new HashMap<Integer, Group>();
			SemanticGraph graph = sentence.getDependancyGraph();
			getChildrenGroups(graph.getRoots(), sentence, graph, groups);
			
			sentence.addGroupMap(groups);
			
		}
		
		return input;
	}

	private Group createGroup(IndexedWord indexedWord, Sentence sentence, SemanticGraph graph, Map<Integer, Group> groups) {
		int index = getIndex(indexedWord);
		if(groups.containsKey(index)) return groups.get(index);
		
		Token token = sentence.getToken(index);
		Group group = new Group(token);
		
		group.addGraphDChildren(getChildrenGroups(graph.getChildren(indexedWord), sentence, graph, groups));
		
		groups.put(index, group);
		return group;
	}

	private List<Group> getChildrenGroups(Collection<IndexedWord> children, Sentence sentence, SemanticGraph graph, Map<Integer, Group> groups) {
		List<Group> list = new LinkedList<Group>();
		
		for (IndexedWord child : children) {
			list.add(createGroup(child, sentence, graph, groups));
		}
		
		return list;
	}

	private int getIndex(IndexedWord indexedWord) {
		return indexedWord.index() - 1;
	}

}
