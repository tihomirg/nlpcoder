package selection.serializers;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import selection.Config;
import selection.DataSerializer;
import selection.DeclarationParserOne;
import selection.DeclarationParserPipeline;
import selection.DeclarationParserTwo;
import selection.GroupWordExtractor;
import selection.IDeclarationParser;
import selection.IWordExtractor;
import selection.WordExtractorEmpty;
import selection.WordProcessor;
import selection.loaders.FolderLoader;
import selection.loaders.IJarLoader;
import selection.loaders.TargetJarLoader;
import selection.types.TypeFactory;
import definitions.ClassInfo;

public class TargetSerialaizer {

	private String targetPackage;
	private TypeFactory factory;	
	
	public TargetSerialaizer(TypeFactory factory, String targetPackage) {
		this.factory = factory;
		this.targetPackage = targetPackage;
	}

	public void serialize(String folderName, String storageLocation, IWordExtractor extractor) {
		File folder = new File(folderName);
		
		try {
			
			FolderLoader fLoader = new FolderLoader();
			List<String> jars = fLoader.getJars(folder);
			IJarLoader jLoder = new TargetJarLoader(Config.getMaxFilesToScan(), targetPackage);
			Map<String, ClassInfo> classFiles = jLoder.getClassFiles(jars, extractor);
			
			Collection<ClassInfo> values = classFiles.values();
			ClassInfo[] values2 = values.toArray(new ClassInfo[values.size()]);
			TypeSerializer serializer = new TypeSerializer(factory);
			serializer.writeObject(storageLocation, values2);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		TypeFactory factory = new TypeFactory();
		TargetSerialaizer loader = new TargetSerialaizer(factory, "java.lang");
		
		IWordExtractor extractor = new WordExtractorEmpty();
		
		loader.serialize(Config.getJarfolder(), Config.getStorageLocation(), extractor);
	
	}
}
