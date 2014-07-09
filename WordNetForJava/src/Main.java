import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import nlp.parser.Token;
import nlp.parser.one.WordTagger;
import api.StabileAPI;
import config.Config;
import definitions.Declaration;
import deserializers.Deserializer;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.POS;
import types.NameGenerator;
import util.Pair;
import wordnet.WordNet;


public class Main {
	private static Map<String, Map<String, Integer>> frequency = new HashMap<String, Map<String,Integer>>();
	private static int treshold = 5;
	private static int fTreshold;


	public static void main(String[] args) {
		NameGenerator nameGen = new NameGenerator(Config.getDeserializerVariablePrefix());
		Deserializer deserializer = new Deserializer();
		StabileAPI api = new StabileAPI(deserializer.deserialize(Config.getSecondStorageLocation()), nameGen);

		List<Declaration> uniqueDecls = api.getUniqueDecls();


		for (Declaration decl : uniqueDecls) {
			add(decl.getSimpleNameTokens());
			add(decl.getReceiverTokens());
			addAll(decl.getArgTokens());
			add(decl.getClazzTokens());
			addAll(decl.getAdditionalReceiverTokens());
			add(decl.getReturnTypeTokens());
		}

		//		for (Entry<String, Map<String, Integer>> entry : frequency.entrySet()) {
		//			String pos = entry.getKey();
		//			Map<String, Integer> map = entry.getValue();
		//			
		//			for (Entry<String, Integer> word : map.entrySet()) {
		//				System.out.println("("+word.getKey()+", "+pos+"): "+word.getValue());
		//			}
		//		}

		//If we have word that have frequency higher that threshold then:
		//- set of their related words is empty
		//- otherwise we just filter out the related words.

		int makeFrequency = getFrequency(new Pair<String, String>("make", "V"));
		int getFrequency = getFrequency(new Pair<String, String>("get", "V"));
		int writeFrequency = getFrequency(new Pair<String, String>("write", "V"));
		int copyFrequency = getFrequency(new Pair<String, String>("copy", "V"));
		
		int fileFrequency = getFrequency(new Pair<String, String>("file", "N"));
		int lineFrequency = getFrequency(new Pair<String, String>("line", "N"));
	
		int inFrequency = getFrequency(new Pair<String, String>("in", "I"));
		int toFrequency = getFrequency(new Pair<String, String>("to", "T"));	
		int aFrequency = getFrequency(new Pair<String, String>("a", "D"));
		int theFrequency = getFrequency(new Pair<String, String>("the", "D"));
		int onFrequency = getFrequency(new Pair<String, String>("on", "O"));
		int fromFrequency = getFrequency(new Pair<String, String>("from", "F"));	
		
		fTreshold = 500;//toFrequency;
		
		System.out.println("Frewquencies: ");
		System.out.println("Make: "+makeFrequency);
		System.out.println("Get: "+getFrequency);
		System.out.println("Write: "+writeFrequency);
		System.out.println("Copy: "+copyFrequency);
		System.out.println("File: "+fileFrequency);
		System.out.println("Line: "+lineFrequency);
		
		System.out.println("In: "+inFrequency);
		System.out.println("To: "+toFrequency);
		System.out.println("A: "+aFrequency);
		System.out.println("The: "+theFrequency);
		System.out.println("On: "+onFrequency);
		System.out.println("From: "+fromFrequency);		
		System.out.println();
		
		WordNet wordNet = new WordNet();
		WordTagger tagger = new WordTagger();
		IIndexWord word = wordNet.getWord("create", POS.VERB);

		String lemma = word.getLemma();
		String pos = transformPos(word.getPOS().toString());

		Pair<String, String> wordPair = new Pair<String, String>(lemma, pos);		
		System.out.println(wordPair);

		List<Pair<String, ISynset>> glosses = wordNet.getGlosses(word);

		for (Pair<String, ISynset> pair : glosses) {
			System.out.println("Word: ");
			
			String gloss = pair.getFirst();
			System.out.println(gloss);

			String firstGloss = getFirstGloss(gloss);
			String[] taggedGloss = tagger.tagSentence(firstGloss);

			List<Pair<String, String>> processedTaggedGloss = processTaggedGloss(taggedGloss, wordNet);

			System.out.println(Arrays.toString(taggedGloss));
			System.out.println(processedTaggedGloss);

			double glossScore = glossScore(processedTaggedGloss);
			System.out.println("Count Score: "+glossScore);
			double glossFrequencyScore = glossFrequencyScore(processedTaggedGloss);
			System.out.println("Freq. Score: "+glossFrequencyScore);
			
			System.out.println("T Score: "+(glossScore *glossFrequencyScore));

			ISynset iSynset = pair.getSecond();
			System.out.println("Synonyms: "+iSynset.getWords());
			System.out.println();
			
			hyponyms(wordNet, tagger, iSynset);
			
			System.out.println();
			hyperyms(wordNet, tagger, iSynset);
			
			System.out.println("----------------------------------------------------------------------------------------------------------");
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
		}


		//		Iterator<IIndexWord> words = wordNet.getWords(POS.VERB);	
		//		while (words.hasNext()) {
		//			IIndexWord word = words.next();
		//			int wordFreq = getFrequency(word);
		//
		//			String lemma = word.getLemma();
		//			String pos = transformPos(word.getPOS().toString());
		//			
		//			Pair<String, String> wordPair = new Pair<String, String>(lemma, pos);
		//			
		//			if (wordFreq < treshold){
		//				List<Pair<String, String>> relatedWords = wordNet.getRelatedWords(word.getLemma(), word.getPOS());
		//				List<Pair<Pair<String, String>, Integer>> filterWord = filterJavaWords(relatedWords);
		//
		//				System.out.println(wordPair +" "+wordFreq+" : "+filterWord);
		//			} else {
		//				System.out.println(wordPair+" "+wordFreq+": []");
		//			}
		//		}

	}

	private static void hyperyms(WordNet wordNet, WordTagger tagger,
			ISynset iSynset) {
		System.out.println("Hypernyms: ");
		for (ISynset hypernym : wordNet.getHyponyms(iSynset)) {
			String hr_gloss = hypernym.getGloss();
			System.out.println(hr_gloss);

			String hr_firstGloss = getFirstGloss(hr_gloss);
			String[] hr_taggedGloss = tagger.tagSentence(hr_firstGloss);

			List<Pair<String, String>> hr_processedTaggedGloss = processTaggedGloss(hr_taggedGloss, wordNet);

			System.out.println(Arrays.toString(hr_taggedGloss));
			System.out.println(hr_processedTaggedGloss);

			double hr_glossScore = glossScore(hr_processedTaggedGloss);
			System.out.println("Count Score: "+hr_glossScore);
			double hr_glossFrequencyScore = glossFrequencyScore(hr_processedTaggedGloss);
			System.out.println("Freq. Score: "+hr_glossFrequencyScore);
			System.out.println("T Score: "+(hr_glossScore *hr_glossFrequencyScore));
			System.out.println("Synonyms: "+hypernym.getWords());
			System.out.println();
		}
	}

	private static void hyponyms(WordNet wordNet, WordTagger tagger, ISynset iSynset) {
		System.out.println("Hypernyms: ");
		
		for (ISynset hypernym : wordNet.getHypernyms(iSynset)) {
			String hr_gloss = hypernym.getGloss();
			System.out.println(hr_gloss);

			String hr_firstGloss = getFirstGloss(hr_gloss);
			String[] hr_taggedGloss = tagger.tagSentence(hr_firstGloss);

			List<Pair<String, String>> hr_processedTaggedGloss = processTaggedGloss(hr_taggedGloss, wordNet);

			System.out.println(Arrays.toString(hr_taggedGloss));
			System.out.println(hr_processedTaggedGloss);

			double hr_glossScore = glossScore(hr_processedTaggedGloss);
			System.out.println("Count Score: "+hr_glossScore);
			double hr_glossFrequencyScore = glossFrequencyScore(hr_processedTaggedGloss);
			System.out.println("Freq. Score: "+hr_glossFrequencyScore);
			System.out.println("T Score: "+(hr_glossScore *hr_glossFrequencyScore));
			System.out.println("Synonyms: "+hypernym.getWords());
			System.out.println();
		}
	}

	private static double glossScore(List<Pair<String, String>> processedTaggedGloss) {
		double sum = 0;
		for (Pair<String, String> pair : processedTaggedGloss) {
			if(isJavaWord(pair)){
				sum++;
			}
		}
		return sum / processedTaggedGloss.size();
	}

	private static double glossFrequencyScore(List<Pair<String, String>> processedTaggedGloss) {
		double sum = 0;
		for (Pair<String, String> pair : processedTaggedGloss) {
			sum += getFrequencyBelowTreshold(pair);
		}
		return sum / processedTaggedGloss.size();
	}
	
	private static double getFrequencyBelowTreshold(Pair<String, String> pair) {
		double f = getFrequency(pair);
		
		if (f < fTreshold) return f;
		else return 0;
	}

	private static double glossInversFrequencyScore(List<Pair<String, String>> processedTaggedGloss) {
		double sum = 0;
		for (Pair<String, String> pair : processedTaggedGloss) {
			double f = getFrequencyBelowTreshold(pair);
			if (f > 0){
				sum += 1 / f;
			}
		}
		return sum / processedTaggedGloss.size();
	}	

	private static int getFrequency(Pair<String, String> pair) {
		return getFrequency(pair.getFirst(), pair.getSecond());
	}	
	
	private static List<Pair<String, String>> processTaggedGloss(String[] gloss, WordNet wordNet) {
		List<Pair<String, String>> words = new LinkedList<Pair<String,String>>();
		for (String string : gloss) {
			String[] pair = string.split("_");
			String word = pair[0].toLowerCase();
			String pos = pair[1];

			if (Character.isLetter(word.charAt(0))){
				String newPos = transformPos(pos);

				String steam = wordNet.getSteam(word, toWordNetPos(newPos));

				if (steam != null){
					words.add(new Pair<String, String>(steam, newPos));
				} 
				
				else {
					words.add(new Pair<String, String>(word, newPos));
				}
			}
		}
		return words;
	}

	public static POS toWordNetPos(String pos){
		if(pos.equals("N")){
			return POS.NOUN;
		} else if(pos.equals("V")){
			return POS.VERB;
		} else if(pos.equals("J")) {
			return POS.ADJECTIVE;
		} else if (pos.equals("R")){
			return POS.ADVERB;
		} else {
			return POS.NOUN;
		}
	}

	private static String getFirstGloss(String gloss) {
		int index = gloss.indexOf("\"");

		if (index != -1) return gloss.substring(0, index);
		else return gloss;
	}

	private static List<Pair<Pair<String, String>, Integer>> filterJavaWords(List<Pair<String, String>> relatedWords) {
		List<Pair<Pair<String, String>, Integer>> filtered = new LinkedList<Pair<Pair<String,String>, Integer>>();
		for (Pair<String, String> word : relatedWords) {
			if (isJavaWord(word)) filtered.add(new Pair<Pair<String, String>, Integer>(word, getFrequency(word)));
		}
		return filtered;
	}

	private static boolean isJavaWord(Pair<String, String> word) {
		String lemma = word.getFirst();
		String pos = word.getSecond();

		if(frequency.containsKey(pos))
			return frequency.get(pos).containsKey(lemma);
		else return false;
	}

	private static int getFrequency(IIndexWord word) {
		String lemma = word.getLemma();
		String pos = transformPos(word.getPOS().toString());

		return getFrequency(lemma, pos);
	}

	private static int getFrequency(String lemma, String pos) {
		if (!frequency.containsKey(pos)){
			return 0;
		} else {
			Map<String, Integer> map = frequency.get(pos);
			if(!map.containsKey(lemma)){
				return 0;
			} else {
				return map.get(lemma);
			}
		}
	}

	private static String transformPos(String pos){
		return Character.isLetter(pos.charAt(0)) ? Character.toString(pos.charAt(0)).toUpperCase() : pos;
	}	

	private static void addAll(List<List<Token>> tokenss) {
		for (List<Token> tokens : tokenss) {
			add(tokens);
		}
	}

	private static void add(List<Token> tokens) {
		for (Token token : tokens) {
			add(token);
		}
	}


	private static void add(Token token) {
		String pos = token.getPos();
		String lemma = token.getLemma();

		if (!frequency.containsKey(pos)){
			frequency.put(pos, new HashMap<String, Integer>());
		}

		Map<String, Integer> map = frequency.get(pos);

		if (!map.containsKey(lemma)){
			map.put(lemma, 1);
		} else {
			map.put(lemma, map.get(lemma) + 1);
		}
	}
}
