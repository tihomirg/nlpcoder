package definitions;

import java.io.Serializable;
import java.util.Arrays;

import selection.parser.one.Word;

public class Declaration implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6849217608815581633L;
	
	private String name;
	private int argNum;
	private boolean isStatic;
	
	private String retType;
	private String[] argType;
	private String[] typeParams;
	
	private boolean method;
	private boolean constructor;
	private boolean field;

	private String clazz;
	
	private Word[] words;
	
	public Declaration(){}	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getRetType() {
		return retType;
	}

	public void setRetType(String retType) {
		this.retType = retType;
	}

	public String[] getArgType() {
		return argType;
	}

	public void setArgType(String[] argType) {
		this.argType = argType;
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
		String signature = this.name+(this.retType != null? " "+shortType(this.retType):"");

        if(clazz != null && (!constructor || isStatic)) {
            signature += " "+shortType(clazz);
         }		
		
		if (this.argType != null)
			for (String argType : this.argType) {
				signature += (argType != null? " "+shortType(argType):"");
			}
		
		return signature;
	}
	
	private String groupTwo(){
		String signature = (this.retType != null? shortType(this.retType):"");		
		
        if(clazz != null && (!constructor || isStatic)) {
           signature += " "+shortType(clazz);
        }

		if (this.argType != null)
			for (String argType : this.argType) {
				signature += (argType != null? " "+shortType(argType):"");
			}
		
		return signature;
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

	private String shortType(String type) {
		return type.substring(type.lastIndexOf(".")+1);
	}
}