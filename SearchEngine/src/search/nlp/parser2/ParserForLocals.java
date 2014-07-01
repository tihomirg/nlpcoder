package search.nlp.parser2;

import java.util.LinkedList;
import java.util.List;

import types.Type;
import util.Pair;
import api.Local;

public class ParserForLocals implements IParser {

	private List<Local> locals;

	public ParserForLocals() {}

	public void setLocals(List<Local> locals) {
		this.locals = locals;
	}

	@Override
	public Input parse(Input curr) {
		String literalizedText = curr.getLiteralizedText();
		
		List<Pair<Integer, Local>> locals = extractLocals(literalizedText);
		List<Pair<Integer, String>> bools = curr.getBooleanLiterals();
		List<Pair<Integer, String>> strings = curr.getStringLiterals();
		List<Pair<Integer, String>> numbers = curr.getNumberLiterals();
		
		curr.setPreprocessedText(substituteLocals(literalizedText, locals, bools, numbers, strings));
		curr.setLocals(locals);
		
		return curr;
	}
	
	public String substituteLocals(String text, List<Pair<Integer, Local>> locals, List<Pair<Integer, String>> bools, List<Pair<Integer, String>> numbers, List<Pair<Integer, String>> strings) {	
		String localizedText = text;

		for (int i = 0; i < locals.size(); i++) {
			Pair<Integer, Local> pair = locals.get(i);
			int startPos = pair.getFirst();
			Local local = pair.getSecond();
			String name = local.getName();
			Type type = local.getType();

			localizedText = substituteLocal(local, startPos, localizedText);

			int fix = name.length() - type.getPrefix().length();
			fixLocalIndexes(fix, locals, startPos);
			
			fixIndexes(fix, bools, startPos);
			fixIndexes(fix, numbers, startPos);
			fixIndexes(fix, strings, startPos);

		}

		return localizedText;
	}	
	
	private void fixLocalIndexes(int fix, List<Pair<Integer, Local>> pairs, int startPos) {
		for (Pair<Integer, Local> pair : pairs) {
			int startPos2 = pair.getFirst();
			if (startPos2 > startPos){
				pair.setFirst(startPos2 - fix);
			}
		}
	}
	
	private void fixIndexes(int fix, List<Pair<Integer, String>> pairs, int startPos) {
		for (Pair<Integer, String> pair : pairs) {
			int startPos2 = pair.getFirst();
			if (startPos2 > startPos){
				pair.setFirst(startPos2 - fix);
			}
		}
	}

	private String substituteLocal(Local local, int startPos, String text) {
		String prefix = text.substring(0, startPos);
		String postFix = text.substring(startPos + local.getName().length());
		return prefix +local.getType().getPrefix()+ postFix;
	}

	public List<Pair<Integer, Local>> extractLocals(String text) {
		List<Pair<Integer, Local>> locals = new LinkedList<Pair<Integer, Local>>();
		String current = text;

		int index = 0;
		boolean condition = true;
		while(true){
			Local local = startsWithLocal(current);
			if (condition && local != null){
				String name = local.getName();
				if (current.length() == name.length()){
					locals.add(new Pair<Integer, Local>(index, local));
					break;
				} else {
					char space = current.charAt(name.length());
					if (!Character.isLetter(space)){
						locals.add(new Pair<Integer, Local>(index, local));						
					}

					index += name.length();
					current = current.substring(name.length());
					condition = false;
				}
			} else {
				if (current.length() > 0){
					char space = current.charAt(0);
					current = current.substring(1);
					condition = !Character.isLetter(space);
					index++;
				} else break;
			}
		}

		return locals;
	}

	private void getHeadLocal(String current) {
		
	}

	private Local startsWithLocal(String current) {
		for (Local local: locals) {
			if (current.startsWith(local.getName())) return local;
		}
		
		return null;
	}	
}
