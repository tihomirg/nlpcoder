package oldcorpus;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LoadOldCorpus {
	
	private static final int FACTOR = 10;

	public static class Decl {

		private String name;
		private int argNum;
		private int count;
		private double prob;
		
		public Decl(String name, int argNum, int count) {
			this(name, argNum, count, 0.0);
		}

		public Decl(String name, int argNum, int count, double prob) {
			this.name = name;
			this.argNum = argNum;
			this.count = count;
			this.prob = prob;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getArgNum() {
			return argNum;
		}

		public void setArgNum(int argNum) {
			this.argNum = argNum;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public double getProb() {
			return prob;
		}

		public void setProb(double prob) {
			this.prob = prob;
		}

		@Override
		public String toString() {
			return "Decl [name=" + name + ", argNum=" + argNum + ", count="
					+ count + ", prob=" + prob + "]\n";
		}

	}

	public static void main(String[] args) {
    	List<Decl> decls = makeDecls(filter(readFile("oldcorpus.txt")));
    	
    	int count = totalCount(decls);   	
    	assignProbs(decls, count);
    	
    	//System.out.println(decls);

    	Map<String, Map<Integer, Double>> map = makeMap(decls);
    	
    	System.out.println(map);
	
	}

	private static Map<String, Map<Integer, Double>> makeMap(List<Decl> decls) {
		Map<String, Map<Integer, Double>>  map = new HashMap<String, Map<Integer,Double>>();
		for (Decl decl : decls) {
			if (!map.containsKey(decl.getName())){
				HashMap<Integer, Double> map2 = new HashMap<Integer, Double>();
				map2.put(decl.getArgNum(), decl.getProb());
				map.put(decl.getName(), map2);
			} else {
				Map<Integer, Double> map2 = map.get(decl.getName());
				map2.put(decl.getArgNum(), decl.getProb());
			}
		}

		return map;
	}

	private static void assignProbs(List<Decl> makeDecls, int count) {
		for (Decl decl : makeDecls) {
			decl.setProb(((double) FACTOR * decl.getCount()) /count);
		}
	}

	private static int totalCount(List<Decl> makeDecls) {
		int count = 0;
    	for (Decl decl : makeDecls) {
			count += decl.getCount();
		}
		return count;
	}

	private static List<Decl> makeDecls(List<String> textdels) {
		List<Decl> decls = new LinkedList<Decl>();
		
		for (String textdecl : textdels) {
			decls.add(parseDecl(textdecl));
		}
		return decls;
	}

	private static Decl parseDecl(String textdecl) {
		String[] splits = textdecl.split(";");
		
		String nameArgs = splits[0];
		String name = nameArgs.substring(0, nameArgs.indexOf(" "));
		String args = nameArgs.substring(nameArgs.indexOf(" "));
		
		return new Decl(getName(name), getArgNum(args), getNum(splits[1]));
	}

	private static int getNum(String string) {
		return Integer.parseInt(string);
	}

	private static String getName(String textdecl) {
		if (textdecl.endsWith("<init>")){
			String firstName = textdecl.substring(0, textdecl.lastIndexOf("."));
			String lastName = firstName.substring(firstName.lastIndexOf(".")+1, firstName.length());
			return firstName +"."+lastName;
		} else return textdecl;
	}

	private static int getArgNum(String textdecl) {
		if (textdecl.equals(" ")){
			return 0;
		} else {
			return textdecl.split(",").length;
		}
	}

	private static List<String> filter(List<String> readFile) {
		List<String> list = new LinkedList<String>();
		for (String string : readFile) {
			if (!string.startsWith("scala.")){
				list.add(string);
			}
		}
		return list;
	}

	private static List<String> readFile(String filename) {
		BufferedReader br = null;
		List<String> list = new LinkedList<String>();
		try {
			br = new BufferedReader(new FileReader(filename));
			String line = null;
			while((line = br.readLine()) != null){
				list.add(line);
			}
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		return list;
	}
}
