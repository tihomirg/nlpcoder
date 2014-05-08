package tests;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nlp.extractors.WordExtractorEmpty;

import org.apache.bcel.classfile.ClassParser;

import config.Config;

import api.InitialAPI;

import types.InitialTypeFactory;
import types.NameGenerator;
import types.PolymorphicType;
import types.StabileTypeFactory;
import types.Type;
import types.TypeFactory;

import definitions.ClassInfo;
import definitions.Declaration;

public class TestInheritedClasses {
	public static void main(String[] args) {
		try{

			//Serilaization part
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File("C:/Users/gvero/git/nlpcoder/APIExtractor/bin/test/T1.class")));

			InitialTypeFactory factory = new InitialTypeFactory(new NameGenerator(Config.getSerializationVariablePrefix()));
						
			InitialAPI cif = new InitialAPI(factory);
			ClassInfo classInfo = cif.createClassInfo(new ClassParser(bis, null).parse());
			
			Type instType = factory.createPolymorphicType(classInfo.getName(), classInfo, new Type[]{factory.createPrimitiveType("java.lang.Integer")});
			
			
			//Deserilaization part
			StabileTypeFactory sfactory = new StabileTypeFactory(new NameGenerator(Config.getDeserializerVariablePrefix()));
			
			List<Type> instantiated = classInfo.getInstantiatedInheritedTypes(instType, sfactory);
			
			System.out.println(instantiated);
			
			List<Declaration> instDecls = classInfo.getInstantiatedDeclarations(instType, sfactory);
			
			System.out.println(instDecls);
			
			//Map<Type, ClassInfo> selectInheried = classInfo.selectInheried();
			
			//System.out.println(selectInheried);
			
			
//			Map<String, ClassInfo> classes = ClassInfo.getClasses();
//			
//			Collection<ClassInfo> values = classes.values();
//			
//			System.out.println(values);
//			
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
