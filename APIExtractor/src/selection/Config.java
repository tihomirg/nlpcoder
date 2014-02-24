package selection;

public class Config {

	private static final String wordNetDictionary = "C:/Program Files/WordNet/2.1/dict";
	private static final String tagger = "C:/Users/gvero/git/lib/stanford-postagger-2011-04-20/models/left3words-wsj-0-18.tagger"; 
	
	public static String getTaggerLocation(){
		return tagger;
	}

	public static String getWordNetDictionaryLocation() {
		return wordNetDictionary;
	}	
	
}
