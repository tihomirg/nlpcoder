package selection.parser.one;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import selection.Config;

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
//
//	public List<TaggedLemma> getSynonyms(String rWord, POS pos){
//		List<TaggedLemma> syn = new LinkedList<TaggedLemma>();
//		// look up first sense of the word "dog "
//		IIndexWord idxWord = dict.getIndexWord(rWord, pos);
//		if (idxWord != null){
//			for(IWordID wordID : idxWord.getWordIDs()){
//				syn.addAll(getSynonyms(wordID));
//			}
//		}
//		return syn;
//	}
//
//	private List<TaggedLemma> getSynonyms(IWordID wordID) {
//		List<TaggedLemma> syn = new LinkedList<TaggedLemma>();
//		IWord word = dict.getWord(wordID);
//		ISynset synset = word.getSynset();
//
//		// iterate over words associated with the synset
//		for(IWord w : synset.getWords())
//			syn.add(new TaggedLemma(w.getLemma(), w.getPOS()));
//		return syn;
//	}
//	
//	public List<TaggedLemma> getHypernyms (String rWord, POS pos){
//		return get(rWord,pos,Pointer.HYPERNYM);
//	}
//	
//	public List<TaggedLemma> getHyponyms (String rWord, POS pos){
//		return get(rWord,pos,Pointer.HYPONYM);
//	}	
//	
//	public List<TaggedLemma> get(String rWord, POS pos, Pointer pointer){
//		List<TaggedLemma> syn = new LinkedList<TaggedLemma>();
//		// look up first sense of the word "dog "
//		IIndexWord idxWord = dict.getIndexWord(rWord, pos);
//		if (idxWord != null){
//			for(IWordID wordID : idxWord.getWordIDs()){
//				syn.addAll(get(wordID, pointer));
//			}
//		}
//		return syn;
//	}
//	
//	private List<TaggedLemma> get(IWordID wordID, Pointer pointer) {
//			List<TaggedLemma> syn = new LinkedList<TaggedLemma>();
//
//			IWord word = dict.getWord(wordID);
//			ISynset synset = word.getSynset();
//			List<ISynsetID> hypernyms = synset.getRelatedSynsets(Pointer.HYPERNYM);
//
//			List<IWord> words;
//			for( ISynsetID sid : hypernyms ){
//				words = dict.getSynset(sid).getWords();
//				for(Iterator<IWord> i = words.iterator(); i.hasNext();){
//					IWord next = i.next();
//					syn.add(new TaggedLemma(next.getLemma(), next.getPOS()));
//				}
//			}
//			return syn;
//	}	
	
	public List<String> getSteams(String word, POS pos){
		return stemmer.findStems(word, pos);
	}
	
	public String getSteam(String word, POS pos){
		List<String> findStems = stemmer.findStems(word, pos);
		if (findStems != null && !findStems.isEmpty()) return findStems.get(0);
		else return null;
	}

	public List<ISynset> getSynonyms(Word word) {
		List<ISynset> synsets = new LinkedList<ISynset>();
		IIndexWord idxWord = dict.getIndexWord(word.getLemma(), word.getPos());
		if (idxWord != null){
			for(IWordID wordID : idxWord.getWordIDs()){
				synsets.add(dict.getSynset(wordID.getSynsetID()));
			}
		}
		
		return synsets;
	}

	public List<Word> getWords(ISynset synset) {
		List<Word> words = new LinkedList<Word>();
		List<IWord> wordsI = synset.getWords();
		for (IWord iWord : wordsI) {
			words.add(new Word(iWord.getLemma(),iWord.getPOS()));
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
		for (ISynsetID iSynsetID : relatedSynsets) {
			synsets.add(dict.getSynset(iSynsetID));
		}
		return synsets;
	}
}
