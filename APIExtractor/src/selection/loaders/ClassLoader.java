package selection.loaders;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

import selection.ParserPipeline;
import selection.WordExtractorFromName;
import selection.parser.one.SentenceZero;

import definitions.Declaration;

public class ClassLoader implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2259468326063857573L;

	private Declaration[] methods;
	private Declaration[] fields;
	
	public ClassLoader(){}
	
	public ClassLoader(String fileName, WordExtractorFromName extractor) throws IOException {
		this(new ClassParser(fileName), extractor);
	}	
	
	public ClassLoader(InputStream in, WordExtractorFromName extractor){
		this(new ClassParser(in, null), extractor);
	}
	
	public ClassLoader(ClassParser parser, WordExtractorFromName extractor) {
		try {
			JavaClass clazz = parser.parse();
			this.methods = initMethods(clazz, extractor);
			this.fields = initFields(clazz, extractor);
			
		} catch (ClassFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Declaration[] getDeclarations(){
		int length = this.methods.length + this.fields.length;
		Declaration[] decls = new Declaration[length];
	
		System.arraycopy(this.methods, 0, decls, 0, this.methods.length);
		System.arraycopy(this.fields, 0, decls, this.methods.length, this.fields.length);	
		
		return decls;
	}

	private Declaration[] initMethods(JavaClass clazz, WordExtractorFromName extractor) {
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
				} else {
					decl.setName(method.getName());
					decl.setMethod(true);
				}
				
				decl.setArgNum(method.getArgumentTypes().length);
				decl.setStatic(method.isStatic());
				
				decl.setWords(extractor.getWords(decl));
				
				decls.add(decl);				
			}
		}
		
		return decls.toArray(new Declaration[decls.size()]);
	}
	
	private Declaration[] initFields(JavaClass clazz, WordExtractorFromName extractor) {
		Field[] fields = clazz.getFields();
		
		List<Declaration> decls = new ArrayList<Declaration>();
		
		for(Field field: fields){
			if(field.isPublic()){
				Declaration decl = new Declaration();
				decl.setName(field.getName());
				decl.setField(true);
				decl.setStatic(field.isStatic());
				
				decl.setWords(extractor.getWords(decl));
				
				decls.add(decl);
			}
		}
		return decls.toArray(new Declaration[decls.size()]);
	}
	
//	public JavaClass getClazz() {
//		return clazz;
//	}

	public Declaration[] getMethods() {
		return methods;
	}

	public void setMethods(Declaration[] methods) {
		this.methods = methods;
	}

	public Declaration[] getFields() {
		return fields;
	}

	public void setFields(Declaration[] fields) {
		this.fields = fields;
	}

	@Override
	public String toString() {
		return "ClassLoader [methods=" + Arrays.toString(methods) + ", fields="
				+ Arrays.toString(fields) + "]\n";
	}
}
