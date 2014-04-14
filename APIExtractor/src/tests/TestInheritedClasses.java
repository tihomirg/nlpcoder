package tests;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.bcel.classfile.ClassParser;

import selection.Config;
import selection.WordExtractorEmpty;
import selection.types.NameGenerator;
import selection.types.Polymorphic;
import selection.types.Type;
import selection.types.TypeFactory;

import definitions.ClassInfo;
import definitions.Declaration;

public class TestInheritedClasses {
	public static void main(String[] args) {
		try{

			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File("C:/Users/gvero/git/nlpcoder/APIExtractor/bin/test/T1.class")));

			TypeFactory factory = new TypeFactory(new NameGenerator(Config.getSerializationVariablePrefix()));
			
			ClassInfo classInfo = new ClassInfo(new ClassParser(bis, null).parse(), new WordExtractorEmpty());
			
			Type instType = factory.createPolymorphic("test.T1", new Type[]{factory.createConst("java.lang.Integer")});
			
			Type[] instantiated = classInfo.getInstantiatedInheritedTypes(instType);
			
			System.out.println(Arrays.toString(instantiated));
			
			List<Declaration> instDecls = classInfo.getInstantiatedDeclarations(instType);
			
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
