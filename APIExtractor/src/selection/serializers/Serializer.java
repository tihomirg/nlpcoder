package selection.serializers;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import definitions.ClassInfo;
import definitions.Declaration;

import selection.Config;
import selection.DataSerializer;
import selection.DeclarationParserOne;
import selection.DeclarationParserPipeline;
import selection.DeclarationParserTwo;
import selection.GroupWordExtractor;
import selection.IDeclarationParser;
import selection.WordExtractor;
import selection.WordProcessor;
import selection.loaders.BoundedClassInfoLoader;
import selection.loaders.FolderLoader;
import selection.loaders.ClassInfoLoader;
import selection.parser.one.ParserOne;
import selection.types.InitialTypeFactory;
import selection.types.NameGenerator;
import selection.types.TypeFactory;
import tests.TypeSerializer;

public class Serializer {

	private InitialTypeFactory factory;
	
	public Serializer(InitialTypeFactory factory) {
		this.factory = factory;
	}

	public void serialize(String folderName, String storageLocation, WordExtractor extractor) {
		File folder = new File(folderName);
		
		try {
			
			FolderLoader fLoader = new FolderLoader();
			List<String> jars = fLoader.getJars(folder);
			ClassInfoLoader jLoader = new BoundedClassInfoLoader(Config.getMaxFilesToScan());
			jLoader.load(jars, factory);			
			Collection<ClassInfo> classes = jLoader.getClasses();
			
			extractor.addWords(classes);
			
			TypeSerializer serializer = new TypeSerializer(factory);
			
			serializer.writeObject(storageLocation, classes.toArray(new ClassInfo[classes.size()]));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		InitialTypeFactory factory = new InitialTypeFactory(new NameGenerator(Config.getSerializationVariablePrefix()));
		Serializer loader = new Serializer(factory);
		
		WordProcessor wordProcessor = new WordProcessor();
		WordExtractor extractor  = new GroupWordExtractor(new DeclarationParserPipeline(new IDeclarationParser[]{new DeclarationParserOne(wordProcessor)}));
		
		loader.serialize(Config.getJarfolder(), Config.getStorageLocation(), extractor);
	
	}
}
