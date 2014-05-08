package tests;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nlp.extractors.WordExtractorEmpty;

import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ArrayType;
import org.apache.bcel.generic.BasicType;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.ReferenceType;
import org.eclipse.jdt.core.Signature;

import config.Config;

import api.InitialAPI;

import types.InitialTypeFactory;
import types.NameGenerator;
import types.Type;
import types.TypeFactory;


import definitions.ClassInfo;
import definitions.Declaration;

public class BcelMain {
	
	public static void main(String[] args){
		try {
			
			InitialTypeFactory factory = new InitialTypeFactory(new NameGenerator(Config.getSerializationVariablePrefix()));
			
			String fileName = "C:/users/gvero/git/nlpcoder/APIExtractor/bin/test/CityImpl.class";
			
			String output = "file.json";
			
			ClassParser cp = new ClassParser(fileName);
			JavaClass parsed = cp.parse();
			
			InitialAPI cif = new InitialAPI(factory);			
			cif.createClassInfo(parsed);
					
			for (ClassInfo classInfo : cif.getClasses()) {
				System.out.println(classInfo);
				System.out.println();
				System.out.println(Arrays.toString(classInfo.getUniqueDeclarations()));
				System.out.println();
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
