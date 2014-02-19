package tests;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.event.SpellChecker;

import definitions.Declaration;
import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.morph.WordnetStemmer;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class SynsetWordSearchMain {
	
	private static final int THRESHOLD = 10;
	private Map<BigSet, Set<Integer>> map = new HashMap<BigSet, Set<Integer>>();
	
	private BigSetMap wordbs;
	
	private ArrayList<Declaration> array = new ArrayList<Declaration>();

	private IDictionary dict;
	
	private WordnetStemmer stemmer;
	
	private MaxentTagger tagger;
	private SpellDictionaryHashMap dictionary;
	private SpellChecker spellChecker;	
	
	public SynsetWordSearchMain() {
		// construct the dictionary object and open it
		try {
			dict = new Dictionary(new URL("file", null , "C:/Program Files/WordNet/2.1/dict"));
			dict.open();
			stemmer = new WordnetStemmer(dict);
			wordbs = new BigSetMap(dict);
			
			tagger = new MaxentTagger("C:/Users/gvero/git/lib/stanford-postagger-2011-04-20/models/left3words-wsj-0-18.tagger");
			
    		dictionary = new SpellDictionaryHashMap(new File("C:/Users/gvero/git/dictionary/eng_com.dic"));
    		spellChecker = new SpellChecker(dictionary);			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public BigSetMap getWordbs() {
		return wordbs;
	}


	public void setWordbs(BigSetMap wordbs) {
		this.wordbs = wordbs;
	}


	public static void main(String[] args) {
		SynsetWordSearchMain synSearch = new SynsetWordSearchMain();
		File jars = new File("C:/Users/gvero/git/jars");
		
		synSearch.scanAll(jars);
		
		String name = "west sells me";
		
		
		
//		List<WordPOS> taggedWords = synSearch.getTaggedWords(synSearch.makeRowSentence(name));
//		List<BigSet> bsets = new ArrayList<BigSet>();
//		
//		for(WordPOS wp: taggedWords){
//		  String word = wp.getWord();
//		  POS pos = wp.getPos();
//		  
//		  System.out.println(word+"   "+pos);
//		  
//		  List<String> steams = synSearch.getSteams(word, pos);
//		  
//		  if (steams.size() > 0){
//			  System.out.println(steams.get(0));
//			  
//			  Set<ISynsetID> synsets = synSearch.getSynsets(steams.get(0), pos);
//			  
//			  if (!synsets.isEmpty()){
//				  BigSet bigSet = new BigSet(synsets);		 		  
//				  synSearch.getWordbs().add(bigSet);
//				  bsets.add(bigSet);
//			  }
//		  }
//		}
		
		
	}
	
	public void scanAll(File folder) {
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

	private void scan(String jarFile) {
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

	private void print(PrintStream out) {
		for(Entry<BigSet, Set<Integer>> entry: map.entrySet()){
			BigSet word = entry.getKey();
			Set<Integer> set = entry.getValue();
			out.println();
			out.println();
			out.println("Word: "+word);
			out.println(Arrays.toString(set.toArray()));
		}
		
	}

	private void put(Declaration[] decls) {
		for(Declaration decl: decls){
			array.add(decl);
			put(decl, array.size()-1);
		}
		
	}

	private void put(Declaration decl, int declIndex) {
		List<BigSet> bsets = getBsets(decl);
		for(BigSet bset: bsets){
			put(bset, declIndex);
		}
		
	}

	private void put(BigSet word, int decl) {
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
	private List<BigSet> getBsets(Declaration decl) {
		String name = decl.getName();
		return getBsets(name);
	}
	
	private List<BigSet> getBsets(String name) {
		List<WordPOS> taggedWords = getTaggedWords(makeRowSentence(name));
		List<BigSet> bsets = new ArrayList<BigSet>();
		
		for(WordPOS wp: taggedWords){
		  String word = wp.getWord();
		  POS pos = wp.getPos();
		  
		  System.out.println(word+"   "+pos);
		  
		  List<String> steams = getSteams(word, pos);
		  
		  if (steams.size() > 0){
			  System.out.println(steams.get(0));
			  
			  Set<ISynsetID> synsets = getSynsets(steams.get(0), pos);
			  
			  if (!synsets.isEmpty()){
				  BigSet bigSet = new BigSet(synsets);		 		  
				  wordbs.add(bigSet);
				  bsets.add(bigSet);
			  }
		  }
		}
		
		return bsets;
	}


	private String makeRowSentence(String sentence){
		List<String> words = getWords(sentence);
		String s = "";
		for(String word: words){
			String s1 = correct(word);
			if (s1 != null) s += s1+" ";
		}
		return s;
	}

	private List<String> getWords(String sentence) {
		String word ="";
		List<String> words = new ArrayList<String>();
		
	    boolean prevSep = true;
	    
	    boolean prevLower = true;
	    
	    boolean lastAdded = true;
		for(char c: sentence.toCharArray()){
			
		  if (Character.isLetter(c)) {
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
	    	lastAdded = false;
		  } else {
			  if (!lastAdded){
				  lastAdded = true;
				  words.add(word);
				  word = "";
			  }
			  prevSep = true;
		  }
		}
		
		if (!lastAdded) words.add(word);
		
		return words;
	}
	
	public Set<ISynsetID> getSynsets(String rWord, POS pos){
		
		Set<ISynsetID> syn = new HashSet<ISynsetID>();
		IIndexWord idxWord = dict.getIndexWord(rWord, pos);
		if (idxWord != null) {
			for(IWordID wordID : idxWord.getWordIDs()) {
				syn.addAll(getSynsets(wordID));
			}
		}
		return syn;
	}

	//TODO: Include nearby synsets as well.
	private Set<ISynsetID> getSynsets(IWordID wordID) {
		Set<ISynsetID> set = new HashSet<ISynsetID>();
		IWord word = dict.getWord(wordID);
		ISynset synset = word.getSynset();
		set.add(synset.getID());
		return set;
	}
	
	public List<String> getSteams(String word, POS pos){
		return stemmer.findStems(word, pos);
	}
	
	public String correct(String word) {
		String newWord = word;
		while(true){
			List suggestions = spellChecker.getSuggestions(newWord, THRESHOLD);

			if (suggestions.size() > 0) return suggestions.get(0).toString();
			else if (newWord.length() > 1){
				newWord = newWord.substring(0, newWord.length()-1);
			} else return null;
		}
    }
	
	public List<WordPOS> getTaggedWords(String sentence){
		String[] wordTags = tagSentence(sentence);
		List<WordPOS> tagged = new ArrayList<WordPOS>();
		
		for(String wordTag: wordTags){
			String[] splits = wordTag.split("/");
			System.out.println(Arrays.toString(splits));
			if (splits[0].equals("string")){
				tagged.add(new WordPOS(splits[0], POS.NOUN));
			} else {
				String oldPos = splits[1];
				POS pos = null;
				
				if (oldPos.startsWith("V")){
					pos = POS.VERB;
				} else if(oldPos.startsWith("N")){
					pos = POS.NOUN;
				} else if(oldPos.startsWith("J")){
					pos = POS.ADJECTIVE;
				} else if(oldPos.startsWith("R")){
					pos = POS.ADVERB;
				} else pos = POS.NOUN;
				
				tagged.add(new WordPOS(splits[0], pos));
			}
		}
		
		return tagged;
	}
	
	public String[] tagSentence(String sentence){
		return tagger.tagString(sentence).split(" ");
	}
}
