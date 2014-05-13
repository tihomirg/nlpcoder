package config;

public class Config {

	private static final String oldcorpus = "oldcorpus.txt";
	private static final String wordNetDictionary = "C:/Program Files/WordNet/3.1/dict";
	private static final String tagger = "C:/Users/gvero/git/lib/stanford-postagger-full-2014-01-04/models/english-bidirectional-distsim.tagger"; 
	private static final int tagNumber = 5;
	
	//Folder where we keep corpus
	private static final String jarFolder = "C:/Users/gvero/git/jars";
	//private static final String jarFolder = "C:/Users/gvero/git/nlpcoder/APIExtractor/bin/test";
	private static final int maxFilesToScan = 100000;
	
	//serialization
	private static final String storageLocation = "declarations.kryo";

	//Sentence-Word Parser
	private static final int levelNum = 2;
	private static final double[] scores = new double[]{5.0,1.0};
	private static final int intervalRadius = scores.length-1;	
	private static final int maxWords = 2* intervalRadius + 1;
	
	//Number of declarations in sets
	private static final int topSelectedLength = 20;
	private static final double nullProb = 0.1;
	private static final String serializationVarPrefix = "SV";
	private static final String deserializationVarPrefix = "DV";
	private static final double smoothFactor = 0.2;
	private static final String frequencyLocation = "frequences.txt";
	private static final String synthesisStorageLocation = "declarations.kryo";
	private static final String compositionStatisticStorageLocation = "stat.txt";
	private static final String compositionCorpusLocation = "C:\\Users\\gvero\\java_projects\\java_projects";	
	
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

	public static String getDeserializerVariablePrefix() {
		return deserializationVarPrefix;
	}

	public static double getSmoothFactor() {
		return smoothFactor;
	}

	public static String getDeclarationFrequencyLocation() {
		return frequencyLocation;
	}

	public static double[] getScores() {
		return scores;
	}

	public static String getSynthesisStorageLocation() {
		return synthesisStorageLocation ;
	}

	public static String getCompositionStatisticLocation() {
		return compositionStatisticStorageLocation;
	}

	public static String getCompositionCorpusLocation() {
		return compositionCorpusLocation;
	}
	
}
