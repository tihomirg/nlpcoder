package tests;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ArrayType;
import org.apache.bcel.generic.BasicType;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.ReferenceType;
import org.apache.bcel.generic.Type;
import org.eclipse.jdt.core.Signature;

import selection.types.Const;
import selection.types.TypeFactory;


import definitions.Declaration;

public class BcelMain {
	
	public static TypeFactory factory = new TypeFactory();
	
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
		
		System.out.println(Arrays.toString(clazz.getAttributes()));
		
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
				
				Attribute[] attributes = method.getAttributes();
				
				System.out.println("Name: "+method.getName());
				System.out.println("Basic Signature: "+method.getSignature());
				
				for (Attribute attribute : attributes) {
					if (attribute instanceof org.apache.bcel.classfile.Signature){
						org.apache.bcel.classfile.Signature sig = (org.apache.bcel.classfile.Signature) attribute;
						String signature = sig.getSignature();
						
						System.out.println("Signature: "+ signature);
						
						String[] parameterTypes = Signature.getParameterTypes(signature);
						
						System.out.println("Params: "+ Arrays.toString(parameterTypes));
						
						String returnType = Signature.getReturnType(signature);
						
						int count = Signature.getArrayCount(returnType);
						
						System.out.println("ReturType: "+returnType+"   count: "+count);
						
//						String[] typeParams = Signature.getTypeArguments(returnType);
//						
//						String typeName = Signature.getTypeErasure(returnType); 
//						
//						System.out.println("TypeName:" +typeName);
//						System.out.println("TypeParams: "+ Arrays.toString(typeParams));
						
					}
				}
				
				
				//System.out.println(type(method.getReturnType()));
				
				decl.setArgNum(method.getArgumentTypes().length);
				decl.setStatic(method.isStatic());
				decls.add(decl);
			}
		}
		
		return decls;
	}
	
	private static selection.types.Type type(Type type) {
		if (type instanceof BasicType){
			BasicType bType = (BasicType) type;
			return basicType(bType);
		} else if (type instanceof ArrayType){
			ArrayType aType = (ArrayType) type;
			Type elementType = aType.getElementType();
			return arrayType(elementType);
		} else if (type instanceof ObjectType) {
			ObjectType oType = (ObjectType) type;
			return objectType(oType);
		} else if (type instanceof ReferenceType) {
			ReferenceType rType = (ReferenceType) type;
			return refType(rType.toString());
		} else {
			return factory.createConst("ReturnaddressType");
		}
		
	}

	private static selection.types.Type refType(String signature) {
		if (isPolyType(signature)) {
			
			return null;
		} else {
			return factory.createConst(signature);
		}
	}

	private static boolean isPolyType(String signature) {
		// TODO Auto-generated method stub
		return false;
	}

	private static selection.types.Type objectType(ObjectType oType) {
		return factory.createConst(oType.toString());
	}

	private static selection.types.Type arrayType(Type elementType) {
		return factory.createPolymorphic("java.lang.Array", new selection.types.Type[]{type(elementType)});
	}

	private static selection.types.Type basicType(BasicType bType) {
		return factory.createConst(bType.toString());
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
