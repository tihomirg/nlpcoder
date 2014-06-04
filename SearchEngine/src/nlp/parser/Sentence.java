package nlp.parser;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

public class Sentence {

	private CoreMap map;
	private List<Token> tokens;
	private SemanticGraph dependancyGraph;
	private Map<Integer, Group> groups;
	private Tree tree;
	private List<Group> stringLiterals;

	public Sentence(CoreMap map) {
		this.map = map;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}

	public List<Token> getTokens() {
		return tokens;
	}

	public Token getToken(int i){
		return tokens.get(i);
	}

	public void setDependancyGraph(SemanticGraph dependancyGraph) {
		this.dependancyGraph = dependancyGraph;
	}

	public SemanticGraph getDependancyGraph() {
		return dependancyGraph;
	}

	@Override
	public String toString() {
		return "Sentence[\n"+
				"tokens = " + tokens + "\n"+
				"string-L = "+stringLiterals+"\n"+
				"groups = " + groups + "\n"+
				"map = "+ map + "\n"+
				"dependancyGraph = " + dependancyGraph + "\n"+
				"tree = " + tree+ "\n"+
				"]\n";
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}

	public void addGroupMap(Map<Integer, Group> groups) {
		this.groups = groups;
	}

	public Map<Integer, Group> getGroupMap() {
		return this.groups;
	}

	public int size() {
		return this.tokens.size();
	}

	public void setStringLiterals(List<Group> strings) {
		this.stringLiterals = strings;
	}

	public List<Group> getSearchKeyGroups(){
		List<Group> list = new LinkedList<Group>();
		for(Group group : getGroups()){
			if (group.isSearchKey()){
				list.add(group);
			}
		}
		return list;
	}

	public Collection<Group> getGroups() {
		return groups.values();
	}
	
	public List<Group> getRemainingGroups(){
		List<Group> list = new LinkedList<Group>();
		for(Group group : getGroups()){
			if (!group.isSearchKey()){
				list.add(group);
			}
		}
		return list;		
	}

}
