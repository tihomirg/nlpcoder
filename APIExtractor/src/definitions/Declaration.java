package definitions;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import selection.parser.one.Word;
import selection.types.Type;

public class Declaration implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6849217608815581633L;
	
	private String name;
	private int argNum;
	private boolean isStatic;
	private boolean isPublic;

	private Type retType;
	private Type[] argType;
	private String[] typeParams;
	private Type receiverType;
	
	private boolean method;
	private boolean constructor;
	private boolean field;

	private String clazz;
	
	private Word[] words;

	private String simpleName;
	private String packageName;
	
	public Declaration(){}	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.simpleName = getShortName(name);
	}
	
	private String getShortName(String name) {
		return name.substring(name.lastIndexOf(".")+1);
	}	

	public int getArgNum() {
		return argNum;
	}

	public void setArgNum(int argNum) {
		this.argNum = argNum;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	public Type getRetType() {
		return retType;
	}

	public void setRetType(Type retType) {
		this.retType = retType;
	}

	public Type[] getArgType() {
		return argType;
	}

	public void setArgType(Type[] paramTypes) {
		this.argType = paramTypes;
	}

	public String[] getTypeParams() {
		return typeParams;
	}

	public void setTypeParams(String[] typeParams) {
		this.typeParams = typeParams;
	}

	public boolean isMethod() {
		return method;
	}

	public void setMethod(boolean method) {
		this.method = method;
	}

	public boolean isConstructor() {
		return constructor;
	}

	public void setConstructor(boolean constructor) {
		this.constructor = constructor;
	}

	public boolean isField() {
		return field;
	}

	public void setField(boolean field) {
		this.field = field;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public Word[] getWords() {
		return words;
	}

	public void setWords(Word[] words) {
		this.words = words;
	}

	@Override
	public String toString() {
		return "Declaration [name=" + name + ", clazz=" + clazz + ", isStatic="
				+ isStatic + ", argNum=" + argNum + ", method=" + method +", words="+Arrays.toString(words)+"]\n";
	}

	public String getSignature() {
		String signature = this.name+(this.retType != null? " "+typeToSentence(this.retType):"");

        if(clazz != null && (!constructor || isStatic)) {
            signature += " "+shortType(clazz);
         }		
		
		if (this.argType != null)
			for (Type argType : this.argType) {
				signature += (argType != null? " "+typeToSentence(argType):"");
			}
		
		return signature;
	}
	
	private String groupTwo(){
		String signature = (this.retType != null? typeToSentence(this.retType):"");		
		
        if(clazz != null && (!constructor || isStatic)) {
           signature += " "+shortType(clazz);
        }

		if (this.argType != null)
			for (Type argType : this.argType) {
				signature += (argType != null? " "+typeToSentence(argType):"");
			}
		
		return signature;
	}

	private String shortType(String clazz) {
		return clazz.substring(clazz.lastIndexOf("."));
	}

	public String getFullName(){
		String prefix = "";
		if (clazz != null && !clazz.equals("")){
			prefix += clazz+".";
		}
		return prefix + name;
	}
	
	public String[] getGroups(){
		return new String[]{this.name, groupTwo()};
	}

	private String typeToSentence(Type type) {
		return toString(type.caracteristicWords());
	}

	private String toString(List<String> words) {
		String s = "";
		for (String word : words) {
		  	s += " "+word; 
		}
		return s;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public boolean overrides(Declaration decl) {
		return simpleName.equals(decl.simpleName);
	}

	public void setReceiverType(Type receiverType) {
		this.receiverType = receiverType;
	}

	public Type getReceiverType() {
		return receiverType;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
}