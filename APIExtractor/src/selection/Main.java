package selection;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import edu.mit.jwi.item.POS;

import selection.loaders.BoundedJarLoader;
import selection.loaders.ClassLoader;
import selection.loaders.FolderLoader;
import selection.loaders.IJarLoader;
import selection.loaders.JarLoder;
import selection.parser.one.ParserOne;
import selection.parser.one.SentenceZero;
import selection.parser.one.Word;
import selection.parser.three.ParserThree;
import selection.parser.two.ParserTwo;

public class Main {

	public static void main(String[] args) {

		FolderLoader fLoader = new FolderLoader();
		
		File folder = new File(Config.getJarfolder());
		
		try {
			
			WordProcessor wordProcessor = new WordProcessor();
			List<String> jars = fLoader.getJars(folder);
			IJarLoader jLoder = new BoundedJarLoader(Config.getMaxFilesToScan());
			
			WordExtractorFromName extractor = new WordExtractorFromName(new ParserPipeline(new IParser[]{new ParserOne(wordProcessor), new ParserTwoIndexes()}));
			
			ClassLoader[] classes = jLoder.getClassFiles(jars, extractor);
			
			DataSerializer serializer = new DataSerializer();
			
			serializer.writeObject(Config.getStorageLocation(),classes, classes.getClass());
			
			ClassLoader[] classes2 = (ClassLoader[]) serializer.readObject(Config.getStorageLocation(), ClassLoader[].class);
			
			//System.out.println(Arrays.toString(classes2));
			
			Selection selection = new Selection();
			
			selection.add(classes2);
			
			System.out.println(selection.select(new Word("bit", POS.NOUN)));
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		String sentence = "Make Buffer-printer read.";
//		parse(sentence);
		
		
		//Selection selection = new Selection(new WordExtractorFromName(wordProcessor));
	}

	private static void parse(String sentence, WordProcessor wordProcessor) {
		int intervalDiameter = 2;
		ParserPipeline parser = new ParserPipeline(new IParser[]{
				new ParserOne(wordProcessor), 
				new ParserTwo(wordProcessor.getWordNet(), 3, intervalDiameter),
				new ParserThree(intervalDiameter)
		});

		SentenceZero zero = new SentenceZero(sentence);
		
		ISentence one = parser.parse(zero);
		
		System.out.print(one);
	}
}
