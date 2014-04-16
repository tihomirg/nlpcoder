package tests;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.bcel.classfile.ClassParser;

import selection.Config;
import selection.WordExtractorEmpty;
import selection.types.InitialTypeFactory;
import selection.types.NameGenerator;
import selection.types.PolymorphicType;
import selection.types.StabileTypeFactory;
import selection.types.Type;
import selection.types.TypeFactory;

import definitions.ClassInfo;
import definitions.Declaration;

public class TestInheritedClasses {
	public static void main(String[] args) {
		try{

			//Serilaization part
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File("C:/Users/gvero/git/nlpcoder/APIExtractor/bin/test/T1.class")));

			InitialTypeFactory factory = new InitialTypeFactory(new NameGenerator(Config.getSerializationVariablePrefix()));
						
			ClassInfo classInfo = ClassInfo.getClassInfo(new ClassParser(bis, null).parse(), factory, new HashMap<String, ClassInfo>());
			
			Type instType = factory.createPolymorphicType(classInfo.getName(), classInfo, new Type[]{factory.createPrimitiveType("java.lang.Integer")});
			
			
			//Deserilaization part
			StabileTypeFactory sfactory = new StabileTypeFactory(new NameGenerator(Config.getDeserializerVariablePrefix()));
			
			Type[] instantiated = classInfo.getInstantiatedInheritedTypes(instType, sfactory);
			
			System.out.println(Arrays.toString(instantiated));
			
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
