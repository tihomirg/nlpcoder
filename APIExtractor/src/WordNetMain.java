import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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


public class WordNetMain {

	private IDictionary dict;
	
	private WordnetStemmer stemmer;

	public WordNetMain(){		
		// construct the dictionary object and open it
		try {
			dict = new Dictionary(new URL("file", null , "C:/Program Files/WordNet/2.1/dict"));
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
	
	public List<String> getFirstMeaningSynonyms(String rWord, POS pos){
		List<String> syn = new LinkedList<String>();
		// look up first sense of the word "dog "
		IIndexWord idxWord = dict.getIndexWord (rWord, pos);
		if (idxWord != null){
			IWordID wordID = idxWord.getWordIDs().get(0);
		    if (wordID != null)
			  syn.addAll(getSynonyms(wordID));
		}
		return syn;
	}	
	
	public List<String> getSynonyms(String rWord, POS pos){
		List<String> syn = new LinkedList<String>();
		// look up first sense of the word "dog "
		IIndexWord idxWord = dict.getIndexWord (rWord, pos);
		if (idxWord != null){
			for(IWordID wordID : idxWord.getWordIDs()){
				syn.addAll(getSynonyms(wordID));
			}
		}
		return syn;
	}

	private List<String> getSynonyms(IWordID wordID) {
		List<String> syn = new LinkedList<String>();
		IWord word = dict.getWord( wordID );
		ISynset synset = word.getSynset();

		// iterate over words associated with the synset
		for( IWord w : synset.getWords ())
			syn.add(w.getLemma());
		return syn;
	}
	
	public List<String> getFirstMeaningHypernyms (String rWord, POS pos){
		List<String> hyper = new LinkedList<String>();
		
		IIndexWord idxWord = dict . getIndexWord (rWord, pos);
		IWordID wordID = idxWord . getWordIDs ().get (0) ; // 1st meaning
		IWord word = dict . getWord ( wordID );
		ISynset synset = word . getSynset ();

		// get the hypernyms
		List < ISynsetID > hypernyms = synset.getRelatedSynsets(Pointer.HYPERNYM);

		// print out each h y p e r n y m s id and synonyms
		List <IWord > words ;
		for( ISynsetID sid : hypernyms ){
			words = dict . getSynset (sid). getWords ();
			for( Iterator<IWord > i = words.iterator(); i.hasNext () ;){
				hyper.add(i.next().getLemma());
			}
		}
		return hyper;
	}	

	public static void main(String[] args){
		WordNetMain wn = new WordNetMain();
		
		String word = "keep";
		POS pos = POS.VERB;
		
		List<String> words = new LinkedList<String>();
		
		for(String steam: wn.getSteams(word, pos)){
			words.addAll(wn.getFirstMeaningSynonyms(steam, pos));
			words.addAll(wn.getFirstMeaningHypernyms(steam, pos));
		}
		
		System.out.println(Arrays.toString(words.toArray()));
	}

}
