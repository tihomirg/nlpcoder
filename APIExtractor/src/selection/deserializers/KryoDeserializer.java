package selection.deserializers;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import selection.types.BoxedType;
import selection.types.ConstType;
import selection.types.NoType;
import selection.types.NullType;
import selection.types.PolymorphicType;
import selection.types.PrimitiveType;
import selection.types.StabileTypeFactory;
import selection.types.Variable;
import selection.types.deserializers.BoxedTypeDeserializer;
import selection.types.deserializers.ConstTypeDeserializer;
import selection.types.deserializers.NoTypeDeserializer;
import selection.types.deserializers.NullTypeDeserializer;
import selection.types.deserializers.PolymorphicTypeDeserializer;
import selection.types.deserializers.PrimitiveTypeDeserializer;
import selection.types.deserializers.VariableDeserializer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;

import definitions.ClassInfo;
import definitions.factory.StabileClassInfoFactory;

public class KryoDeserializer {
	
	private final NoTypeDeserializer noTypeSer;
	private final NullTypeDeserializer nullTypeSer;
	private final PrimitiveTypeDeserializer primSer;
	private final ConstTypeDeserializer constSer;
	private final BoxedTypeDeserializer boxedSer;
	private final PolymorphicTypeDeserializer polySer;
	private final VariableDeserializer varSer;
	
	public KryoDeserializer(StabileTypeFactory factory) {
		this.noTypeSer = new NoTypeDeserializer(factory);
		this.nullTypeSer = new NullTypeDeserializer(factory);
		this.primSer = new PrimitiveTypeDeserializer(factory);
		this.constSer = new ConstTypeDeserializer(factory);
		this.boxedSer = new BoxedTypeDeserializer(factory);
		this.polySer = new PolymorphicTypeDeserializer(factory);
		this.varSer = new VariableDeserializer(factory);
	}

	public Object readObject(String file, Class type) {
		
		Object obj = null;
		
		try {
			Input in = new Input(new BufferedInputStream(new FileInputStream(file)));			

			Kryo kryo = new Kryo();
			kryo.register(NoType.class, noTypeSer);
			kryo.register(NullType.class,nullTypeSer);
			kryo.register(PrimitiveType.class, primSer);
			kryo.register(ConstType.class, constSer);
			kryo.register(BoxedType.class, boxedSer);
			kryo.register(PolymorphicType.class, polySer);
			kryo.register(Variable.class, varSer);			
			
			obj = kryo.readObject(in, type);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
}

