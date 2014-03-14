package tests;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;


import definitions.Declaration;

public class BcelMain {
	
	public static void main(String[] args){
		try {
			
			String fileName = "C:/users/gvero/git/nlpcoder/APIExtractor/bin/test/CityImpl.class";
			
			String output = "file.json";
			
			Declaration[] decls = getDeclarations(fileName);
	
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static Declaration[] getDeclarations(InputStream in)
			throws IOException {
		ClassParser parser = new ClassParser(in, null);
		
        return getDeclarations(parser);
	}

	private static Declaration[] getDeclarations(ClassParser parser)
			throws IOException {
		JavaClass clazz = parser.parse();
		
		//System.out.println(clazz);
		
		List<Declaration> decls = getMethods(clazz);
		decls.addAll(getFields(clazz));
		
		Declaration[] declArr = new Declaration[decls.size()];
		
		for(int i=0; i< declArr.length; i++){
			declArr[i] = decls.get(i);
		}
		return declArr;
	}	
	
	
	private static Declaration[] getDeclarations(String fileName)
			throws IOException {
		ClassParser parser = new ClassParser(fileName);
		
		return getDeclarations(parser);
	}

	private static List<Declaration> getMethods(JavaClass clazz) {
		Method[] methods = clazz.getMethods();
		
		List<Declaration> decls = new ArrayList<Declaration>();
		
		for(Method method: methods){
			if(method.isPublic()){
				Declaration decl = new Declaration();
				decl.setClazz(clazz.getClassName());
				
				String name = method.getName();
				if (name.equals("<init>")){
					String clazzName = clazz.getClassName();
					decl.setName(clazzName.substring(clazzName.lastIndexOf('.')+1, clazzName.length()));
					decl.setConstructor(true);
					decl.setMethod(true);					
				} else {
					decl.setName(method.getName());
					decl.setMethod(true);
				}
				
				decl.setArgNum(method.getArgumentTypes().length);
				decl.setStatic(method.isStatic());
				decls.add(decl);
			}
		}
		
		return decls;
	}
	
	private static List<Declaration> getFields(JavaClass clazz) {
		Field[] fields = clazz.getFields();
		
		List<Declaration> decls = new ArrayList<Declaration>();
		
		for(Field field: fields){
			if(field.isPublic()){
				Declaration decl = new Declaration();
				decl.setName(field.getName());
				decl.setField(true);
				decl.setStatic(field.isStatic());
				decls.add(decl);
			}
		}
		return decls;
	}	
	
}
