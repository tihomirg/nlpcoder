package builders;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.TypeParameter;
import org.eclipse.jdt.core.dom.WildcardType;

import selection.types.Type;
import selection.types.TypeFactory;

public class TypeBuilder extends ASTVisitor {

	private TypeFactory factory;
	
	private Type result;
	
	public TypeBuilder(TypeFactory factory) {
		this.factory = factory;
	}	
	
    //------------------------------------------------------ Types ------------------------------------------------------
	
	public boolean visit(ArrayType node) {
		result = createArrayType(node.getDimensions(), node.getElementType());
		return false;
	}
	
	private Type createArrayType(int dimensions, org.eclipse.jdt.core.dom.Type elementType) {
		if (dimensions > 0){
			return factory.createPolymorphic("java.lang.Array", new Type[]{createArrayType(dimensions, elementType)});
		} else {
			return createType(elementType);
		}
	}

	private Type getResult() {
		return result;
	}

	public boolean visit(ParameterizedType node) {
		org.eclipse.jdt.core.dom.Type type = node.getType();
		List<org.eclipse.jdt.core.dom.Type> typeArguments = node.typeArguments();
		
		String name = type.toString();
		
		int size = typeArguments.size();
		Type[] types = new Type[size];
		
		for(int i = 0; i < size; i++){
		  types[i] = createType(typeArguments.get(i));	
		}

		result = factory.createPolymorphic(name, types);	
		return false;
	}
	
	private Type createType(org.eclipse.jdt.core.dom.Type type) {
		type.accept(this);
		return this.getResult();
	}

	public boolean visit(PrimitiveType node) {
		result = factory.createConst(node.toString());
		return false;
	}
	
	public boolean visit(QualifiedType node) {
		result = factory.createConst(makeQualifiedName(node));
		return false;
	}	

	private String makeQualifiedName(QualifiedType node) {
		Type type = createType(node.getQualifier());
		return type.getHead() +"$"+node.getName().getIdentifier();
	}

	public boolean visit(SimpleType node) {
		result = factory.createConst(node.toString());
		return false;
	}

	public boolean visit(TypeParameter node) {
		throw new RuntimeException("TypeParameter in TypeBuilder not supported!");
	}	
	
	public boolean visit(WildcardType node) {
		result = factory.createConst("?");
		return false;
	}	
}
