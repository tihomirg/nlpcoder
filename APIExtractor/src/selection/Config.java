package selection;

public class Config {

	private static final String oldcorpus = "oldcorpus.txt";
	private static final String wordNetDictionary = "C:/Program Files/WordNet/3.1/dict";
	private static final String tagger = "C:/Users/gvero/git/lib/stanford-postagger-full-2014-01-04/models/english-bidirectional-distsim.tagger"; 
	private static final int tagNumber = 5;
	
	//Folder where we keep corpus
	private static final String jarFolder = "C:/Users/gvero/git/jars";
	private static final int maxFilesToScan = 100000;
	
	//serialization
	private static final String storageLocation = "declarations.kryo";
	
	//Sentence-Word Parser
	private static final int levelNum = 1;
	private static final int intervalRadius = 2;
	private static final int maxWords = 2* intervalRadius + 1;	

	//Number of declarations in sets
	private static final int topSelectedLength = 20;
	private static final double nullProb = (0.1 / maxWords);
	private static final String serializationVarPrefix = "SV";	
	
	public static String getTaggerLocation(){
		return tagger;
	}

	public static String getWordNetDictionaryLocation() {
		return wordNetDictionary;
	}

	public static int getNumOfTags() {
		return tagNumber;
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

	public static int getNumOfLevels() {
		// TODO Auto-generated method stub
		return levelNum;
	}

	public static int getIntervalRadius() {
		// TODO Auto-generated method stub
		return intervalRadius;
	}

	public static int getMaxWords() {
		return maxWords;
	}

	public static double getNullProbability() {
		return nullProb;
	}

	public static String getOldCorpusLocation() {
		return oldcorpus;
	}

	public static String getSerializationVariablePrefix() {
		return serializationVarPrefix;
	}
	
}
