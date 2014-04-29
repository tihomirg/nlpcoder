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

public class KryoDeserializer {
	
	public Object readObject(String file, Class type) {
		
		Object obj = null;
		
		try {
			Input in = new Input(new BufferedInputStream(new FileInputStream(file)));			

			Kryo kryo = new Kryo();
//			kryo.register(NoType.class, noTypeSer);
//			kryo.register(NullType.class,nullTypeSer);
//			kryo.register(PrimitiveType.class, primSer);
//			kryo.register(ConstType.class, constSer);
//			kryo.register(BoxedType.class, boxedSer);
//			kryo.register(PolymorphicType.class, polySer);
//			kryo.register(Variable.class, varSer);			
			//kryo.register(ClassInfo.class, classSer);
			
			obj = kryo.readObject(in, type);
			
			//in.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
}

