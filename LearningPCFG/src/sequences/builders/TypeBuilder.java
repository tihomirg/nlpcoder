package sequences.builders;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.TypeParameter;
import org.eclipse.jdt.core.dom.WildcardType;
import declarations.Imported;
import selection.types.ReferenceType;
import selection.types.Type;
import selection.types.TypeFactory;

public class TypeBuilder extends ASTVisitor {

	private TypeFactory factory;
	
	private Type result;
	private ReferenceTypeBuilder rtb;
		
	public TypeBuilder(TypeFactory factory, Imported imported) {
		this.factory = factory;
		this.rtb = new ReferenceTypeBuilder(factory, imported);
	}	
	
    //------------------------------------------------------ Types ------------------------------------------------------
	
	public boolean visit(ArrayType node) {
		result = rtb.createArrayType(node);
		return false;
	}
	
	public Type getResult() {
		return result;
	}

	public boolean visit(ParameterizedType node) {
		result = rtb.createParameterizedType(node);
		return false;
	}
	
	public Type createType(org.eclipse.jdt.core.dom.Type type) {
		this.result = null;
		type.accept(this);
		return this.getResult();
	}
	
	public ReferenceType createReferenceType(org.eclipse.jdt.core.dom.Type type) {
		return this.rtb.createReferenceType(type);
	}	

	public boolean visit(PrimitiveType node) {
		result = factory.createPrimitiveType(node.toString());
		return false;
	}
	
	public boolean visit(QualifiedType node) {
		result = rtb.createQualifiedType(node);
		return false;
	}
	
	public boolean visit(SimpleType node) {
		result = rtb.createSimpleType(node);
		return false;
	}

	public boolean visit(TypeParameter node) {
		result = rtb.createTypeParameter(node);
		return false;
	}	
	
	public boolean visit(WildcardType node) {
		result = rtb.createWildcardType(node);
		return false;
	}	
}
