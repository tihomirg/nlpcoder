package selection.serializers;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import selection.Config;
import selection.types.BoxedType;
import selection.types.ConstType;
import selection.types.InitialTypeFactory;
import selection.types.NameGenerator;
import selection.types.NoType;
import selection.types.NullType;
import selection.types.PolymorphicType;
import selection.types.PrimitiveType;
import selection.types.TypeFactory;
import selection.types.Variable;
import selection.types.serializers.BoxedTypeSerializer;
import selection.types.serializers.ConstTypeSerializer;
import selection.types.serializers.NoTypeSerializer;
import selection.types.serializers.NullTypeSerializer;
import selection.types.serializers.PolymorphicTypeSerializer;
import selection.types.serializers.PrimitiveTypeSerializer;
import selection.types.serializers.VariableSerializer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;

public class TypeSerializer {
	
	private final NoTypeSerializer noTypeSer = new NoTypeSerializer();
	private final NullTypeSerializer nullTypeSer = new NullTypeSerializer();
	private final PrimitiveTypeSerializer primSer = new PrimitiveTypeSerializer();
	private final ConstTypeSerializer constSer = new ConstTypeSerializer();
	private final BoxedTypeSerializer boxedSer = new BoxedTypeSerializer();
	private final PolymorphicTypeSerializer polySer = new PolymorphicTypeSerializer();
	private final VariableSerializer varSer = new VariableSerializer();

	public void writeObject(String file, Object obj) {
		try {
			Output out = new Output(new BufferedOutputStream(new FileOutputStream(file)));

			Kryo kryo = new Kryo();
			kryo.register(NoType.class, noTypeSer);
			kryo.register(NullType.class,nullTypeSer);
			kryo.register(PrimitiveType.class, primSer);
			kryo.register(ConstType.class, constSer);
			kryo.register(BoxedType.class, boxedSer);
			kryo.register(PolymorphicType.class, polySer);
			kryo.register(Variable.class, varSer);
			
			kryo.writeObject(out, obj);

			out.flush();
			out.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		TypeFactory factory = new InitialTypeFactory(new NameGenerator(Config.getSerializationVariablePrefix()));
		TypeSerializer serializer = new TypeSerializer();
		
		String file = "types.kryo";
//		
//		Type cons1 = factory.createConst("java.lang.Integer");
//		Type cons2 = factory.createConst("java.lang.String");
//		
//		PolymorphicType poly = factory.createPolymorphic("java.util.List", new Type[]{cons1});
//		PolymorphicType poly2 = factory.createPolymorphic("java.util.Map", new Type[]{poly, cons2});
//		
//		serializer.writeObject(file, poly2);
//		Object type = serializer.readObject(file, poly2.getClass());
//		
//		System.out.println(type);
//		System.out.println();
//		System.out.println(factory);
	}
}
