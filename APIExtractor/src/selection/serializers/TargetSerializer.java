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
import definitions.ClassInfo;
import definitions.InitialClassInfoFactory;

public class TargetSerializer {

	private String[] targetPackage;
	private InitialClassInfoFactory cif;	
	
	public TargetSerializer(InitialClassInfoFactory cif, String[] targetPackage) {
		this.cif = cif;
		this.targetPackage = targetPackage;
	}

	public void serialize(String folderName, String storageLocation, WordExtractor extractor) {
		File folder = new File(folderName);
		
		try {
			
			FolderLoader fLoader = new FolderLoader();
			List<String> jars = fLoader.getJars(folder);
			ClassInfoLoader jLaoder = new TargetJarLoader(Config.getMaxFilesToScan(), targetPackage, cif);
			jLaoder.load(jars);
			Collection<ClassInfo> classes = jLaoder.getClasses();
			
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
		TargetSerializer loader = new TargetSerializer(classInfoFactory, TargetConfig.getTarget());
		
		WordProcessor wordProcessor = new WordProcessor();
		WordExtractor extractor = new GroupWordExtractor(new DeclarationParserPipeline(new IDeclarationParser[]{new DeclarationParserOne(wordProcessor)}));
		
		loader.serialize(Config.getJarfolder(), Config.getStorageLocation(), extractor);
	
	}	
}
