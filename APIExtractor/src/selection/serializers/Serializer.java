package selection.serializers;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import api.InitialAPI;

import definitions.ClassInfo;
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

	private ClassInfoLoader cil;

	public Serializer(ClassInfoLoader cil) {
		this.cil = cil;
	}

	public void serialize(String folderName, String storageLocation, WordExtractor extractor) {
		try {
			
			cil.load(new FolderLoader().getJars(new File(folderName)));
			cil.loadArrayClassInfo();
			cil.connectTypesAndClassInfos();
			
			Collection<ClassInfo> classes = cil.getClasses();
			
			System.out.println(classes);			
			
			extractor.addWords(classes);
			
			KryoSerializer serializer = new KryoSerializer();
			
			serializer.writeObject(storageLocation, cil.getCif());//classes.toArray(new ClassInfo[classes.size()]));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	public static void main(String[] args) {
		InitialTypeFactory factory = new InitialTypeFactory(new NameGenerator(Config.getSerializationVariablePrefix()));
		InitialAPI cif = new InitialAPI(factory);
		BoundedClassInfoLoader bcil = new BoundedClassInfoLoader(Config.getMaxFilesToScan(), cif);
		
		Serializer loader = new Serializer(bcil);
		
		WordProcessor wordProcessor = new WordProcessor();
		WordExtractor extractor  = new GroupWordExtractor(new DeclarationParserPipeline(new IDeclarationParser[]{new DeclarationParserOne(wordProcessor)}));
		
		loader.serialize(Config.getJarfolder(), Config.getStorageLocation(), extractor);
	
	}
}
