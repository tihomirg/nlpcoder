package tests.unit;

import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.ClassInfo;

import selection.Config;
import selection.deserializers.Deserializer;
import selection.types.BoxedType;
import selection.types.NameGenerator;
import selection.types.PrimitiveType;
import selection.types.StabileTypeFactory;
import selection.types.Type;

public class TestType {

	private static StabileTypeFactory factory;
	private static ClassInfo[] classInfos;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		factory = new StabileTypeFactory(new NameGenerator(Config.getDeserializerVariablePrefix()));
		Deserializer deserializer = new Deserializer(factory);
		classInfos = deserializer.deserialize(Config.getStorageLocation());
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testIsCompatiblePrimitiveToBoxedType() {
		BoxedType boxedInt = factory.createBoxedType(java.lang.Integer.class.getName());
		PrimitiveType primitiveInt = factory.createPrimitiveType("int");
		
		List<Type> compatible = boxedInt.getCompatibleTypes(factory);
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
