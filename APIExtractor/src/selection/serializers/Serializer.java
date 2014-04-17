package selection.serializers;

import java.io.File;
import java.util.Collection;
import java.util.List;
import definitions.ClassInfo;
import definitions.InitialClassInfoFactory;
import selection.Config;
import selection.DeclarationParserOne;
import selection.DeclarationParserPipeline;
import selection.GroupWordExtractor;
import selection.IDeclarationParser;
import selection.WordExtractor;
import selection.WordProcessor;
import selection.loaders.BoundedClassInfoLoader;
import selection.loaders.FolderLoader;
import selection.loaders.ClassInfoLoader;
import selection.types.InitialTypeFactory;
import selection.types.NameGenerator;

public class Serializer {

	private InitialClassInfoFactory cif;
	
	public Serializer(InitialClassInfoFactory cif) {
		this.cif = cif;
	}

	public void serialize(String folderName, String storageLocation, WordExtractor extractor) {
		File folder = new File(folderName);
		
		try {
			
			FolderLoader fLoader = new FolderLoader();
			List<String> jars = fLoader.getJars(folder);
			ClassInfoLoader jLoader = new BoundedClassInfoLoader(Config.getMaxFilesToScan(), cif);
			jLoader.load(jars);			
			Collection<ClassInfo> classes = jLoader.getClasses();
			
			extractor.addWords(classes);
			
			TypeSerializer serializer = new TypeSerializer();
			
			serializer.writeObject(storageLocation, classes.toArray(new ClassInfo[classes.size()]));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		InitialTypeFactory factory = new InitialTypeFactory(new NameGenerator(Config.getSerializationVariablePrefix()));
		InitialClassInfoFactory classInfoFactory = new InitialClassInfoFactory(factory);
		Serializer loader = new Serializer(classInfoFactory);
		
		WordProcessor wordProcessor = new WordProcessor();
		WordExtractor extractor  = new GroupWordExtractor(new DeclarationParserPipeline(new IDeclarationParser[]{new DeclarationParserOne(wordProcessor)}));
		
		loader.serialize(Config.getJarfolder(), Config.getStorageLocation(), extractor);
	
	}
}
