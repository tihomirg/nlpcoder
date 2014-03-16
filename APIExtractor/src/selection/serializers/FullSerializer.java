package selection.serializers;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import definitions.ClassInfo;

import selection.Config;
import selection.DataSerializer;
import selection.DeclarationParserOne;
import selection.DeclarationParserPipeline;
import selection.DeclarationParserTwo;
import selection.GroupWordExtractor;
import selection.IDeclarationParser;
import selection.IWordExtractor;
import selection.WordProcessor;
import selection.loaders.BoundedJarLoader;
import selection.loaders.FolderLoader;
import selection.loaders.IJarLoader;
import selection.parser.one.ParserOne;
import selection.types.TypeFactory;

public class FullSerializer {

	private TypeFactory factory;
	
	public FullSerializer(TypeFactory factory) {
		this.factory = factory;
	}

	public void serialize(String folderName, String storageLocation, IWordExtractor extractor) {
		File folder = new File(folderName);
		
		try {
			
			FolderLoader fLoader = new FolderLoader();
			List<String> jars = fLoader.getJars(folder);
			IJarLoader jLoder = new BoundedJarLoader(Config.getMaxFilesToScan());
			Map<String, ClassInfo> classFiles = jLoder.getClassFiles(jars, extractor);
			
			Collection<ClassInfo> values = classFiles.values();
			
			ClassInfo[] values2 = values.toArray(new ClassInfo[values.size()]);
			
			//System.out.println(Arrays.toString(classes));
			
			TypeSerializer serializer = new TypeSerializer(factory);
			
			serializer.writeObject(storageLocation, values2);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		TypeFactory factory = new TypeFactory();
		FullSerializer loader = new FullSerializer(factory);
		
		WordProcessor wordProcessor = new WordProcessor();
		//IWordExtractor extractor = new WordExtractorFromSignature(new ParserPipeline(new IParser[]{new ParserOne(wordProcessor), new ParserTwoIndexes()}));
		
		IWordExtractor extractor = new GroupWordExtractor(new DeclarationParserPipeline(new IDeclarationParser[]{new DeclarationParserOne(wordProcessor), new DeclarationParserTwo(0.4, new int[]{2,5}, Config.getNullProbability())}));
		
		
		loader.serialize(Config.getJarfolder(), Config.getStorageLocation(), extractor);
	
	}
}
