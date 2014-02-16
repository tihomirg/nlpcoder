import java.util.Arrays;
import java.util.List;

import definitions.ClassInfo;


public class Parser {

	public static class Term {
		private int lastIndex;
		private String name;
		private String typeArg;

		public int getLastIndex() {
			return lastIndex;
		}
		public void setLastIndex(int lastIndex) {
			this.lastIndex = lastIndex;
		}		
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getTypeArg() {
			return typeArg;
		}
		public void setTypeArg(String typeArg) {
			this.typeArg = typeArg;
		}
		@Override
		public String toString() {
			return "Term [lastIndex=" + lastIndex + ", name=" + name
					+ ", typeArg=" + typeArg + "]";
		}
			
	}
	
	public ClassInfo parse(List<String> content){
		if(content.isEmpty()) return null;
		
		String text = content.get(0);
		
		//ClassInfo clazz = new ClassInfo();
		
		Term termClass = extractTerm(text, "class ",' ');
		
		System.out.println(termClass);
	
		if (text.contains("extends")){
			text = text.substring(termClass.getLastIndex());
			
			Term termSuperclass = extractTerm(text, "extends ",' ');
			
			System.out.println(termSuperclass);
		}
		
		if(text.contains("implements")){
			text = text.substring(termClass.getLastIndex());
			
			Term termSuperclass = extractTerm(text, "implements ",' ');
			
			System.out.println(termSuperclass);
			
			while(true){
			  text = text.substring(termSuperclass.getLastIndex());
			
		      termSuperclass = extractTerm(text, "",' ');
			
		      if (!termSuperclass.getName().equals("{")) break;
			  System.out.println(termSuperclass);
			}
		}
		
		
		return null;
	}
	
	private Term extractTerm(String text, String start, char end) {
		Term term = new Term();
		
		int firstIndex = text.indexOf(start)+ start.length();
		
		String rest = text.substring(firstIndex);
		
		int count = 0;
		char curr = '$';
		String name = "";
		String typeArg = "";
		int i = 0;
		for(; i<rest.length() && !(curr==end && count == 0); i++){
			curr = rest.charAt(i);
			
			if (count > 0){
				if (!(count == 1 && curr=='>')) typeArg += curr;
			}			
			
			if (curr == '<'){
				count++;
			} else if (curr == '>'){
				count--;
			} else {
				if (count == 0 && curr!=end) name += curr;
			}
		}
		
		term.setName(name.replace(",",""));
		term.setTypeArg(typeArg);
		term.setLastIndex(firstIndex+i);
		
		return term;
	}

	private void extractTypeArgs(String typeArgs, ClassInfo clazz) {
		// TODO Auto-generated method stub
		System.out.println(typeArgs);
	}

	public <T> int findIndex(T[] array, T symbol){
		for(int i = 0; i<array.length; i++){
		  if (symbol.equals(array[i])){
			  return i;
		  }
		}
		return -1;
	}
	
}
