package sequences;

import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.TypeParameter;
import org.eclipse.jdt.core.dom.WildcardType;

import api.Imported;

import definitions.ClassInfo;

import selection.types.PolymorphicType;
import selection.types.ReferenceType;
import selection.types.Type;
import selection.types.TypeFactory;

public class ReferenceTypeBuilder extends ASTVisitor {

	private TypeFactory factory;
	private Imported imported;
	
	private ReferenceType result;
	private ClassInfo arrayClazz;

	public ReferenceTypeBuilder(TypeFactory factory, Imported imported) {
		this.factory = factory;
		this.imported = imported;
		
		this.arrayClazz = imported.getFirstType("Array");
	}	

	//------------------------------------------------------ Types ------------------------------------------------------

	public boolean visit(ArrayType node) {
		result = createArrayType(node.getDimensions(), node.getElementType());
		return false;
	}

	private ReferenceType createArrayType(int dimensions, org.eclipse.jdt.core.dom.Type elementType) {
		if (dimensions > 0){
			return factory.createPolymorphicType("java.lang.Array", arrayClazz, new Type[]{createArrayType(dimensions - 1, elementType)});
		} else {
			return createReferenceType(elementType);
		}
	}

	public ReferenceType getResult() {
		return result;
	}

	public boolean visit(ParameterizedType node) {
		org.eclipse.jdt.core.dom.Type type = node.getType();

		String name = type.toString();
		if (imported.isImportedType(name)) {
			ClassInfo clazz = imported.getFirstType(name);
			
			List<org.eclipse.jdt.core.dom.Type> typeArguments = node.typeArguments();
			int size = typeArguments.size();

			Type[] types = new Type[size];
			for(int i = 0; i < size; i++){
				types[i] = createReferenceType(typeArguments.get(i));	
			}

			result = factory.createPolymorphicType(clazz, types);
		} else result = factory.createNoVariableType();
		return false;
	}

	public ReferenceType createParameterizedType(ParameterizedType node) {
		this.result = null;
		visit(node);
		return this.getResult();
	}		

	public ReferenceType createReferenceType(org.eclipse.jdt.core.dom.Type type) {
		this.result = null;
		type.accept(this);
		return this.getResult();
	}

	public ReferenceType createArrayType(ArrayType node){
		this.result = null;
		visit(node);
		return this.getResult();
	}

	public ReferenceType createQualifiedType(QualifiedType node) {
		this.result = null;
		visit(node);
		return this.getResult();
	}

	public ReferenceType createTypeParameter(TypeParameter node) {
		this.result = null;
		visit(node);
		return this.getResult();
	}

	public ReferenceType createWildcardType(WildcardType node) {
		this.result = null;
		visit(node);
		return this.getResult();
	}	

	public ReferenceType createSimpleType(SimpleType node) {
		this.result = null;
		visit(node);
		return this.getResult();
	}	

	public boolean visit(PrimitiveType node) {
		result = factory.createBoxedType(node.toString());
		return false;
	}

	public boolean visit(QualifiedType node) {
		String name = makeQualifiedName(node);
		if (imported.isImportedType(name)) {
			ClassInfo clazz = imported.getFirstType(name);
			result = factory.createConstType(clazz.getName());
		} else result = factory.createNoVariableType();
		return false;
	}	

	private String makeQualifiedName(QualifiedType node) {
		return node.getQualifier() +"$"+node.getName().getIdentifier();
	}

	public boolean visit(SimpleType node) {
		String name = node.toString();
		if (imported.isImportedType(name)) {
			ClassInfo clazz = imported.getFirstType(name);
			result = factory.createConstType(clazz.getName());
		} else result = factory.createNoVariableType();

		return false;
	}

	public boolean visit(TypeParameter node) {
		result = factory.createNoVariableType();
		return false;
	}	

	public boolean visit(WildcardType node) {
		result = factory.createNewVariable();
		return false;
	}

}
