package selection.serializers;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import selection.Config;
import selection.DeclarationParserOne;
import selection.DeclarationParserPipeline;
import selection.DeclarationParserTwo;
import selection.GroupWordExtractor;
import selection.IDeclarationParser;
import selection.WordExtractor;
import selection.WordProcessor;
import selection.loaders.FolderLoader;
import selection.loaders.ClassInfoLoader;
import selection.loaders.TargetJarLoader;
import selection.serializers.config.TargetConfig;
import selection.types.InitialTypeFactory;
import selection.types.NameGenerator;
import selection.types.TypeFactory;
import tests.TypeSerializer;
import definitions.ClassInfo;

public class TargetSerializer {

	private String[] targetPackage;
	private InitialTypeFactory factory;	
	
	public TargetSerializer(InitialTypeFactory factory, String[] targetPackage) {
		this.factory = factory;
		this.targetPackage = targetPackage;
	}

	public void serialize(String folderName, String storageLocation, WordExtractor extractor) {
		File folder = new File(folderName);
		
		try {
			
			FolderLoader fLoader = new FolderLoader();
			List<String> jars = fLoader.getJars(folder);
			ClassInfoLoader jLaoder = new TargetJarLoader(Config.getMaxFilesToScan(), targetPackage);
			jLaoder.load(jars, factory);
			Collection<ClassInfo> classes = jLaoder.getClasses();
			
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
		TargetSerializer loader = new TargetSerializer(factory, TargetConfig.getTarget());
		
		WordProcessor wordProcessor = new WordProcessor();
		WordExtractor extractor = new GroupWordExtractor(new DeclarationParserPipeline(new IDeclarationParser[]{new DeclarationParserOne(wordProcessor)}));
		
		loader.serialize(Config.getJarfolder(), Config.getStorageLocation(), extractor);
	
	}	
}
