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
import org.eclipse.jdt.core.Signature;

import selection.types.Const;
import selection.types.Type;
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
				
				
				String signature = null;
				
				Attribute[] attributes = method.getAttributes();
				
				System.out.println("Method: "+method.getName());
				
				for (Attribute attribute : attributes) {
					if (attribute instanceof org.apache.bcel.classfile.Signature){
						signature = ((org.apache.bcel.classfile.Signature) attribute).getSignature();
						break;
					}
				}

				if (signature == null){
					signature = method.getSignature();
				}
				
				Type returnType = returnType(signature);
				System.out.println("Ret. type: "+ returnType);
				decl.setRetType(returnType);
				Type[] parameterTypes = parameterTypes(signature);
				System.out.println("Args: "+ Arrays.toString(parameterTypes));
				decl.setArgType(parameterTypes);
				
				decl.setArgNum(method.getArgumentTypes().length);
				decl.setStatic(method.isStatic());
				decls.add(decl);
			}
		}
		
		return decls;
	}
	
	private static Type[] parameterTypes(String signature) {
		String[] parameterTypes = Signature.getParameterTypes(signature);
		int length = parameterTypes.length;
		Type[] types = new Type[length];
		for (int i = 0; i < length; i++) {
			types[i] = type(parameterTypes[i]);
		}
		return types;
	}

	private static Type returnType(String signature) {
		return type(Signature.getReturnType(signature));
	}

	private static Type type(String type) {
		if (isArrayType(type)){
			int dimension = Signature.getArrayCount(type);
			String elementType = Signature.getElementType(type);
			return arrayType(elementType, dimension);
		} else if (isPolymorphicType(type)) {
			String[] typeParams = Signature.getTypeArguments(type);
			String typeErasure = Signature.getTypeErasure(type);
			return polyType(typeErasure, typeParams);
		} else {
			String dotSignature = dottedTransformation(type);
			return factory.createConst(dotSignature);
		}
	}

	protected static String dottedTransformation(String type) {
		return dottedName(Signature.toString(type));
	}

	private static String dottedName(String string) {
		return string.replace("/", ".");
	}

	private static Type polyType(String typeErasure, String[] typeParams) {
		String name = Signature.toString(typeErasure);
		return factory.createPolymorphic(dottedName(name), types(typeParams));
	}

	private static Type[] types(String[] signatures) {
		int length = signatures.length;
		Type[] types = new Type[length];
		for (int i = 0; i < length; i++) {
			types[i] = type(signatures[i]);
		}
		return types;
	}

	private static Type arrayType(String elementType, int dimension) {
		if (dimension > 0){
		return factory.createPolymorphic("java.lang.Array", new Type[]{arrayType(elementType, dimension - 1)});	
		} else {
			return type(elementType);
		}
	}

	private static boolean isPolymorphicType(String type) {
		return Signature.getTypeArguments(type).length > 0;
	}

	private static boolean isArrayType(String type) {
		return Signature.getArrayCount(type) > 0;
	}
	
	private static List<Declaration> getFields(JavaClass clazz) {
		Field[] fields = clazz.getFields();
		
		List<Declaration> decls = new ArrayList<Declaration>();
		
		for(Field field: fields){
			if(field.isPublic()){
				Declaration decl = new Declaration();
				decl.setName(field.getName());
				decl.setField(true);
				
				String signature = null;
				
				Attribute[] attributes = field.getAttributes();
				
				for (Attribute attribute : attributes) {
					if (attribute instanceof org.apache.bcel.classfile.Signature){
						signature = ((org.apache.bcel.classfile.Signature) attribute).getSignature();
						break;
					}
				}

				if (signature == null){
					signature = field.getSignature();
				}
				
				Type returnType = type(signature);
				
				System.out.println("Field: "+field.getName());
				System.out.println("Type: "+returnType);
				
				decl.setRetType(returnType);				
				
				decl.setStatic(field.isStatic());
				decls.add(decl);
			}
		}
		return decls;
	}	
	
}
