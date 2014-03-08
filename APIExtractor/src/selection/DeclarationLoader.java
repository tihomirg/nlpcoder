package selection;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import selection.loaders.BoundedJarLoader;
import selection.loaders.ClassLoader;
import selection.loaders.FolderLoader;
import selection.loaders.IJarLoader;
import selection.parser.one.ParserOne;

public class DeclarationLoader {

	public void serialize(String folderName, String storageLocation,
			IWordExtractor extractor) {
		File folder = new File(folderName);
		
		try {
			
			FolderLoader fLoader = new FolderLoader();
			List<String> jars = fLoader.getJars(folder);
			IJarLoader jLoder = new BoundedJarLoader(Config.getMaxFilesToScan());
			ClassLoader[] classes = jLoder.getClassFiles(jars, extractor);
			
			//System.out.println(Arrays.toString(classes));
			
			DataSerializer serializer = new DataSerializer();
			serializer.writeObject(storageLocation,classes, classes.getClass());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ClassLoader[] deserialize(String storageLocation) {
		DataSerializer serializer = new DataSerializer();
		return (ClassLoader[]) serializer.readObject(storageLocation, ClassLoader[].class);
	}
	
	public static void main(String[] args) {
		DeclarationLoader loader = new DeclarationLoader();
		
		WordProcessor wordProcessor = new WordProcessor();
		//IWordExtractor extractor = new WordExtractorFromSignature(new ParserPipeline(new IParser[]{new ParserOne(wordProcessor), new ParserTwoIndexes()}));
		
		IWordExtractor extractor = new GroupWordExtractor(new DeclarationParserPipeline(new IDeclarationParser[]{new DeclarationParserOne(wordProcessor), new DeclarationParserTwo(0.4, new int[]{2,5}, Config.getNullProbability())}));
		
		
		loader.serialize(Config.getJarfolder(), Config.getStorageLocation(), extractor);
	
	}
}
