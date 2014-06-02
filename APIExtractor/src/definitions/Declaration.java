package definitions;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import nlp.parser.Token;
import nlp.parser.one.Word;
import types.StabileTypeFactory;
import types.Type;

public class Declaration implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6849217608815581633L;

	private static int idGen = 0; 
	private int id = idGen++;

	private String name;
	private int argNum;
	private boolean isStatic;
	private boolean isPublic;

	private Type retType;
	private Type[] argTypes;
	private Type receiverType;

	private boolean method;
	private boolean constructor;
	private boolean field;
	private boolean literal;

	private String clazz;

	private Word[] words;
	
	private Token[] tokens;

	private String simpleName;
	private String packageName;

	private Declaration unique;

	private Token[] receiverTokens;

	public Declaration(){}	

	public Declaration(String name, Type retType, boolean isLiteral) {

		this.name = name;
		this.retType = retType;

		if(isLiteral){
			this.literal = isLiteral;
			this.isPublic = true;
		}

	}

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

	public String getLongName(){
		return this.clazz +"."+ this.name;
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

	public Type[] getArgTypes() {
		return argTypes;
	}

	public void setArgTypes(Type[] argTypes) {
		this.argTypes = argTypes;
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

	public String getSignature() {
		String signature = this.name+(this.retType != null? " "+typeToSentence(this.retType):"");

		if(clazz != null && (!constructor || isStatic)) {
			signature += " "+shortType(clazz);
		}		

		if (this.argTypes != null)
			for (Type argType : this.argTypes) {
				signature += (argType != null? " "+typeToSentence(argType):"");
			}

		return signature;
	}

	private String groupOne(){
		String one = this.simpleName;

		one += (this.retType != null? " "+typeToSentence(this.retType):"");

		if(clazz != null && (!constructor || isStatic)) {
			one += " "+shortType(clazz);
		}

		return one;
	}

	private String groupTwo(){
		String two = "";

		if (this.argTypes != null)
			for (Type argType : this.argTypes) {
				two += (argType != null? " "+typeToSentence(argType):"");
			}

		return two;
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

	public String[] getCaracteristicWordGroups(){
		return new String[]{groupOne(), groupTwo()};
	}

	public List<String> getTokensAsStrings(){
		List<String> words = new LinkedList<String>();

		words.add(simpleName);
		
		if (this.isStatic){
			if (this.clazz != null){
				words.add(shortType(clazz));
			}

		} else {
			if (!constructor){
				if (this.clazz != null){
					words.add(shortType(clazz));
				}
				
				if (this.receiverType != null){
					words.addAll(this.receiverType.getWords());
				}
			}
		}

		if (this.argTypes != null) {
			for (Type argType : this.argTypes) {
				if (argType != null){
					words.addAll(argType.getWords());
				}
			}		
		}

		if (this.retType != null) words.addAll(this.retType.getWords());

		return words;

	}

	private String typeToSentence(Type type) {
		return toString(type.getWords());
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

	/*
	 * We invoke this method in context of inheritance graph.
	 */
	public boolean equivalentTo(Declaration decl) {
		//same simple name
		if (!simpleName.equals(decl.simpleName)) return false;
		Type[] thatArgTypes = decl.argTypes;
		int length = thatArgTypes.length;		
		if (length != this.argTypes.length) return false;

		for (int i=0; i < length ; i++) {
			Type thatType = thatArgTypes[i];
			Type thisType = this.argTypes[i];

			if (thatType.getPrefix() != thisType.getPrefix()) return false;
		}

		return true;
	}

	public void setReceiverType(Type receiverType) {
		this.receiverType = receiverType;
	}

	public Type getReceiverType() {
		return receiverType;
	}

	public boolean hasReceiver(){
		return receiverType != null;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	private String modifiers() {
		if(this.method){
			if (this.constructor) {
				return "constructor";
			} else return "method";
		} else if (field) {
			return "field";
		} else return "error";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isLiteral() {
		return literal;
	}

	public void setLiteral(boolean literal) {
		this.literal = literal;
	}

	@Override
	public Declaration clone() {
		try {
			return (Declaration) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String toString() {
		return modifiers()+" name=" + simpleName 
				+ ", receiver=" + receiverType + ", params="
				+ Arrays.toString(argTypes) + ", ret=" + retType
				+ ", pkg=" + packageName+"\n"
		        + ", tokens=" + Arrays.toString(tokens) + "\n";
	}

	public boolean isCompatible(Type[] argTypes, StabileTypeFactory factory) {
		int length = this.argTypes.length;
		if (length != argTypes.length) return false;

		for (int i = 0; i < length; i++) {
			if(!this.argTypes[i].isCompatible(argTypes[i], factory)) return false;
		}

		return true;
	}

	public Token[] getTokens() {
		return tokens;
	}

	public void setTokens(Token[] tokens) {
		this.tokens = tokens;
	}

	public Declaration getUniqueDecl() {
		return this.unique;
	}

	public Token[] getReceiverTokens() {
		return this.receiverTokens;
	}

	//	public boolean isCompatible(Type[] argTypes, StabileTypeFactory factory) {
	//		if (this.method){
	//			//no receiver
	//			if(this.constructor || this.isStatic){
	//				int length = this.argTypes.length;
	//				if (length != argTypes.length) return false;
	//				
	//				for (int i = 0; i < length; i++) {
	//					if(!this.argTypes[i].isCompatible(argTypes[i], factory)) return false;
	//				}
	//				
	//				return true;
	//			} else {
	//				int length = this.argTypes.length;
	//				if (length + 1 != argTypes.length) return false;
	//				
	//				if(!this.receiverType.isCompatible(argTypes[0], factory)) return false;
	//				
	//				for (int i = 1; i < length; i++) {
	//					if(!this.argTypes[i].isCompatible(argTypes[i], factory)) return false;
	//				}
	//				
	//				return true;				
	//			}
	//		} else {
	//			//field
	//			if (this.isStatic){
	//				return argTypes.length == 0;
	//			} else {
	//				if (argTypes.length != 1) return false;
	//				
	//				if(!this.receiverType.isCompatible(argTypes[0], factory)) return false;
	//				
	//				return true;
	//			}
	//		}
	//	}	
}