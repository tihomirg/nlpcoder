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

import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.serializers.CollectionSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers;
import com.esotericsoftware.kryo.serializers.MapSerializer;

import definitions.Declaration;


public class JarFileScanner {

	private static Map<String, Set<Integer>> map = new HashMap<String, Set<Integer>>();
	private static ArrayList<Declaration> array = new ArrayList<Declaration>();
	
	
	public static void main(String[] args) {
		File jars = new File("C:/Users/gvero/git/jars");
		scanAll(jars);
		
		String out = "jars.txt";
		serialize(out);
		//ArrayList<Declaration> array = (ArrayList<Declaration>) deserializeArray(out);
		
		 Map<String, Set<Integer>> map = (Map<String, Set<Integer>>) deserializeMap(out);
		
		 for (int i : map.get("or")){
			 System.out.println(array.get(i)); 
		 }
		 
		 
		 
		 
		//System.out.println(Arrays.toString(array.toArray()));
	}
	
	private static void serialize(String file){
//		Class type = ArrayList.class;
//		CollectionSerializer serializer = new CollectionSerializer(Declaration.class, null);
//		KryoMain.writeObject(file, array, serializer, type);
		
		Class type2 = HashMap.class;
		MapSerializer serializer2 = new MapSerializer();
		serializer2.setKeyClass(String.class, null);
		CollectionSerializer serializer3 = new CollectionSerializer(Integer.class, null); 
		serializer2.setValueClass(HashSet.class, serializer3);
		
		KryoMain.writeObject(file, map, serializer2, type2);	
	}
	
	private static Object deserializeArray(String file){
		Class type = ArrayList.class;
		CollectionSerializer serializer = new CollectionSerializer(Declaration.class, null);
		return KryoMain.readObject(file, serializer, type);
	}
	
	private static Object deserializeMap(String file){
		Class type2 = HashMap.class;
		MapSerializer serializer2 = new MapSerializer();
		serializer2.setKeyClass(String.class, null);
		CollectionSerializer serializer3 = new CollectionSerializer(Integer.class, null); 
		serializer2.setValueClass(HashSet.class, serializer3);
		return KryoMain.readObject(file, serializer2, type2);
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
		for(Entry<String, Set<Integer>> entry: map.entrySet()){
			String word = entry.getKey();
			Set<Integer> set = entry.getValue();
			out.println();
			out.println();
			out.println("Word: "+word);
			out.println(Arrays.toString(set.toArray()));
		}
		
	}

	private static void put(Declaration[] decls) {
		for(Declaration decl: decls){
			array.add(decl);
			put(decl, array.size()-1);
		}
		
	}

	private static void put(Declaration decl, int declIndex) {
		Set<String> words = getWords(decl);
		for(String word: words){
			put(word, declIndex);
		}
		
	}

	private static void put(String word, int decl) {
		if (!map.containsKey(word)){
			Set<Integer> set = new HashSet<Integer>();
			set.add(decl);
			map.put(word, set);
		} else {
			Set<Integer> set = map.get(word);
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
