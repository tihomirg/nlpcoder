package wordnet;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import nlp.parser.Token;
import config.Config;


import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Pointer;
import edu.mit.jwi.morph.WordnetStemmer;

public class WordNet {

	private IDictionary dict;
	private WordnetStemmer stemmer;

	public WordNet(){
		// construct the dictionary object and open it
		try {
			dict = new Dictionary(new URL("file", null , Config.getWordNetDictionaryLocation()));
			dict.open();
			stemmer = new WordnetStemmer(dict);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public List<String> getSteams(String word, POS pos){
		return stemmer.findStems(word, pos);
	}

	public String getSteam(String word, POS pos){
		List<String> findStems = stemmer.findStems(word, pos);
		if (findStems != null && !findStems.isEmpty()) return findStems.get(0);
		else return null;
	}

	public List<ISynset> getSynonyms(Token token) {
		List<ISynset> synsets = new LinkedList<ISynset>();
		IIndexWord idxWord = dict.getIndexWord(token.getLemma(), token.toWordNetPos());
		if (idxWord != null){
			//IWordID wordID = idxWord.getWordIDs().get(0);
			for(IWordID wordID : idxWord.getWordIDs()){
				synsets.add(dict.getSynset(wordID.getSynsetID()));
			}
		}

		return synsets;
	}

	public List<Token> getTokens(ISynset synset) {
		List<Token> words = new LinkedList<Token>();
		List<IWord> wordsI = synset.getWords();
		for (IWord iWord : wordsI) {
			String lemma = iWord.getLemma();
			words.add(new Token(lemma, lemma, iWord.getPOS().toString(), -1));
		}
		return words;
	}

	public List<ISynset> getNeighbors(List<ISynset> synsets) {
		List<ISynset> nSynsets = new LinkedList<ISynset>();
		for (ISynset iSynset : synsets) {
			nSynsets.addAll(getHypernyms(iSynset));
			nSynsets.addAll(getHyponyms(iSynset));
		}
		return nSynsets;
	}

	private List<ISynset> getHyponyms(ISynset iSynset) {
		return get(iSynset, Pointer.HYPONYM);
	}

	private List<ISynset> getHypernyms(ISynset iSynset) {
		return get(iSynset, Pointer.HYPERNYM);
	}

	private List<ISynset> get(ISynset iSynset, Pointer pointer) {
		List<ISynset> synsets = new LinkedList<ISynset>();
		List<ISynsetID> relatedSynsets = iSynset.getRelatedSynsets(pointer);
		if (relatedSynsets != null && !relatedSynsets.isEmpty()){
			//ISynsetID iSynsetID = relatedSynsets.get(0);
			for (ISynsetID iSynsetID : relatedSynsets) {
				synsets.add(dict.getSynset(iSynsetID));
			}
		}
		return synsets;
	}

	public List<Token> getRelatedTokens(Token token){
		List<ISynset> synonyms = getSynonyms(token);
		List<Token> tokens = new LinkedList<Token>();
		tokens.addAll(getTokens(synonyms));
		tokens.addAll(getTokens(getNeighbors(synonyms)));
		return tokens;
	}

	private List<Token> getTokens(List<ISynset> synonyms) {
		List<Token> tokens = new LinkedList<Token>();
		for (ISynset iSynset : synonyms) {
			tokens.addAll(getTokens(iSynset));
		}
		return tokens;
	}
}
