import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import definitions.Declaration;


public class JarFileScanner {

	private static Map<String, Set<Declaration>> map = new HashMap<String, Set<Declaration>>();
	
	public static void main(String[] args) {
		File jars = new File("C:/Users/gvero/git/jars");
		scanAll(jars);
		
		File output = new File("jars.txt");
		print(output);		
	}
	
	private static void print(File file) {
		// TODO Auto-generated method stub

		PrintStream output = null;
		try {
			output = new PrintStream(file);
			print(output);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (output != null) {
			output.flush();
			output.close();
		}		
		
	}

	public static void scanAll(File folder) {
	    for (File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            scanAll(fileEntry);
	        } else {
	        	if (fileEntry.isFile() && fileEntry.getName().endsWith(".jar")){
	               scan(fileEntry.getAbsolutePath());
	        	}
	        }
	    }
	}	

	private static void scan(String jarFile) {
		try {
			JarFile jar = new JarFile(jarFile);

			Enumeration<JarEntry> entries = jar.entries();
			while (entries.hasMoreElements()) {
				JarEntry file = entries.nextElement();

				file.isDirectory();
				if (!file.isDirectory() && file.getName().endsWith(".class")){
					InputStream in = jar.getInputStream(file);
					Declaration[] decls = BcelMain.getDeclarations(in);
					
					put(decls);
					
				}
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void print(PrintStream out) {
		for(Entry<String, Set<Declaration>> entry: map.entrySet()){
			String word = entry.getKey();
			Set<Declaration> set = entry.getValue();
			out.println();
			out.println();
			out.println("Word: "+word);
			out.println(Arrays.toString(set.toArray()));
		}
		
	}

	private static void put(Declaration[] decls) {
		for(Declaration decl: decls){
			put(decl);
		}
		
	}

	private static void put(Declaration decl) {
		Set<String> words = getWords(decl);
		for(String word: words){
			put(word, decl);
		}
		
	}

	private static void put(String word, Declaration decl) {
		if (!map.containsKey(word)){
			Set<Declaration> set = new HashSet<Declaration>();
			set.add(decl);
			map.put(word, set);
		} else {
			Set<Declaration> set = map.get(word);
			set.add(decl);
		}
		
	}

	//TODO improve this with wordnet.
	//Find only lemmas for words and synonyms.
	private static Set<String> getWords(Declaration decl) {
		String name = decl.getName();
		String word ="";
		Set<String> words = new HashSet<String>();
		
	    boolean prevSep = true;
	    
	    boolean prevLower = true;
		for(char c: name.toCharArray()){
			
		  if (Character.isLetter(c)){
			if (Character.isUpperCase(c)){
		    	if ((prevSep || prevLower) && !word.equals("")){
		    	  words.add(word);
		    	  word=Character.toString(Character.toLowerCase(c));
		    	} else {
		    	  word+=Character.toLowerCase(c);
		    	}
		    	prevSep = false;
		    	prevLower = false;
		    } else {
		    	prevLower = true;
		    	word+=c;
		    }
		  } else {
			  prevSep = true;
		  }
		}
		
		words.add(word);
		
		return words;
	}

}
