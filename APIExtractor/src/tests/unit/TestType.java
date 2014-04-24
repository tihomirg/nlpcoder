package tests.unit;

import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.ClassInfo;
import definitions.factory.StabileClassInfoFactory;

import selection.Config;
import selection.deserializers.Deserializer;
import selection.types.BoxedType;
import selection.types.ConstType;
import selection.types.NameGenerator;
import selection.types.PolymorphicType;
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
		
		Assert.isTrue(boxedInt.isCompatible(primitiveInt, factory));
	}
	
	@Test
	public void testIsCompatibleBoxedToPrimitiveType() {
		BoxedType boxedInt = factory.createBoxedType(java.lang.Integer.class.getName());
		PrimitiveType primitiveInt = factory.createPrimitiveType("int");		
		Assert.isTrue(primitiveInt.checkCompatible(boxedInt, factory).isSuccess());
	}	
	
	@Test
	public void testIsNotCompatibleBoxedToPrimitiveType() {
		BoxedType boxedInt = factory.createBoxedType(java.lang.Integer.class.getName());
		PrimitiveType primitiveBool = factory.createPrimitiveType("boolean");		
		Assert.isTrue(!primitiveBool.isCompatible(boxedInt, factory));
	}
	
	@Test
	public void testIsCompatiblePolyType(){
		Type[] params = new Type[]{factory.createBoxedType(java.lang.Integer.class)};
		ClassInfo classInfo = scif.getClassesMap().get(java.util.LinkedList.class.getName());
		PolymorphicType type = factory.createPolymorphicType(java.util.LinkedList.class, classInfo, params);
				
		ClassInfo classInfo2 = scif.getClassesMap().get(java.util.List.class.getName());
		PolymorphicType type2 = factory.createPolymorphicType(java.util.List.class, classInfo2, params);
		
		Assert.isTrue(type2.isCompatible(type, factory));
		Assert.isTrue(!type.isCompatible(type2, factory));
	}
	
	@Test
	public void testIsCompatiblePolyType2(){
		Type[] params = new Type[]{factory.createBoxedType(java.lang.Integer.class)};
		ClassInfo classInfo = scif.getClassesMap().get(java.util.LinkedList.class.getName());
		PolymorphicType type = factory.createPolymorphicType(java.util.LinkedList.class, classInfo, params);
		
		ConstType type2 = factory.createConstType(java.lang.Object.class);	
		Assert.isTrue(type2.isCompatible(type, factory));
	}
	
	@Test
	public void testIsCompatiblePolyType3(){
		Type[] params = new Type[]{factory.createConstType(java.lang.String.class), factory.createBoxedType(java.lang.Integer.class)};
		ClassInfo classInfo = scif.getClassesMap().get(java.util.HashMap.class.getName());
		
		System.out.println(classInfo.getType());
		
		PolymorphicType type = factory.createPolymorphicType(java.util.HashMap.class, classInfo, params);
			
		Type[] params2 = new Type[]{factory.createNewVariable(), factory.createNewVariable()};
		ClassInfo classInfo2 = scif.getClassesMap().get(java.util.HashMap.class.getName());
		PolymorphicType type2 = factory.createPolymorphicType(java.util.HashMap.class, classInfo2, params2);		
		
		System.out.println(type2.getCompatibleTypes(factory));
		
		Assert.isTrue(type2.isCompatible(type, factory));
		Assert.isTrue(type.isCompatible(type2, factory));
	}	
	
}
