package tests.unit;

import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.factory.StabileClassInfoFactory;

import selection.Config;
import selection.deserializers.Deserializer;
import selection.types.BoxedType;
import selection.types.NameGenerator;
import selection.types.PrimitiveType;
import selection.types.StabileTypeFactory;
import selection.types.Type;

public class TestType {

	private static StabileTypeFactory factory;
	private static StabileClassInfoFactory scif;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		NameGenerator nameGen = new NameGenerator(Config.getDeserializerVariablePrefix());
		Deserializer deserializer = new Deserializer(factory);
		scif = new StabileClassInfoFactory(deserializer.deserialize(Config.getStorageLocation()), nameGen);
		factory = scif.getStf();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testIsCompatiblePrimitiveToBoxedType() {
		BoxedType boxedInt = factory.createBoxedType(java.lang.Integer.class.getName());
		PrimitiveType primitiveInt = factory.createPrimitiveType("int");
		
		List<Type> compatible = primitiveInt.getCompatibleTypes(factory);
		
		//TODO: Some bugs in the Class Info needs to be fixed. It seams that not all
		//inherited types and classes are created.
		//TODO: Refactoring
		
		System.out.println(compatible);
		
		Assert.isTrue(boxedInt.isCompatible(primitiveInt, factory).isSuccess());
	}
	
	@Test
	public void testIsCompatibleBoxedToPrimitiveType() {
		BoxedType boxedInt = factory.createBoxedType(java.lang.Integer.class.getName());
		PrimitiveType primitiveInt = factory.createPrimitiveType("int");		
		Assert.isTrue(primitiveInt.isCompatible(boxedInt, factory).isSuccess());
	}	
	
	@Test
	public void testIsNotCompatibleBoxedToPrimitiveType() {
		BoxedType boxedInt = factory.createBoxedType(java.lang.Integer.class.getName());
		PrimitiveType primitiveBool = factory.createPrimitiveType("boolean");		
		Assert.isTrue(!primitiveBool.isCompatible(boxedInt, factory).isSuccess());
	}	

}
