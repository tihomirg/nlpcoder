package selection.loaders;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

import definitions.Declaration;

public class ClassLoader {

	private JavaClass clazz;
	private List<Declaration> methods;
	private List<Declaration> fields;
	
	public ClassLoader(String fileName) throws IOException {
		this(new ClassParser(fileName));
	}	
	
	public ClassLoader(InputStream in){
		this(new ClassParser(in, null));
	}
	
	public ClassLoader(ClassParser parser) {
		try {
			this.clazz = parser.parse();
			this.methods = initMethods();
			this.fields = initFields();
			
		} catch (ClassFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Declaration> getDeclarations() throws IOException {		
		List<Declaration> decls = getMethods();
		decls.addAll(getFields());
		return decls;
	}

	private List<Declaration> initMethods() {
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
				decls.add(decl);
			}
		}
		
		return decls;
	}
	
	private List<Declaration> initFields() {
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
	
	public JavaClass getClazz() {
		return clazz;
	}

	public List<Declaration> getMethods() {
		return methods;
	}

	public List<Declaration> getFields() {
		return fields;
	}	
}
