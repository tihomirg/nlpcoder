package selection;

public class Config {

	private static final String wordNetDictionary = "C:/Program Files/WordNet/2.1/dict";
	private static final String tagger = "C:/Users/gvero/git/lib/stanford-postagger-2011-04-20/models/left3words-wsj-0-18.tagger"; 
	private static final int taggNumber = 5;
	private static final String jarFolder = "C:/Users/gvero/git/jars";
	private static final int maxFilesToScan = 1000;
	private static final String storageLocation = "declarations.kryo";
	private static final int topSelectedLength = 5;	
	
	public static String getTaggerLocation(){
		return tagger;
	}

	public static String getWordNetDictionaryLocation() {
		return wordNetDictionary;
	}

	public static int getNumOfTags() {
		return taggNumber;
	}

	public static String getJarfolder() {
		return jarFolder;
	}

	public static int getMaxFilesToScan() {
		return maxFilesToScan;
	}

	public static String getStorageLocation() {
		return storageLocation;
	}

	public static int topSelectedLength() {
		// TODO Auto-generated method stub
		return topSelectedLength;
	}
	
}
