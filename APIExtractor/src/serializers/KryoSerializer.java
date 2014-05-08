package serializers;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import serializers.types.BoxedTypeSerializer;
import serializers.types.ConstTypeSerializer;
import serializers.types.NoTypeSerializer;
import serializers.types.NullTypeSerializer;
import serializers.types.PolymorphicTypeSerializer;
import serializers.types.PrimitiveTypeSerializer;
import serializers.types.VariableSerializer;
import types.BoxedType;
import types.ConstType;
import types.NoType;
import types.NullType;
import types.PolymorphicType;
import types.PrimitiveType;
import types.Variable;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.ReferenceResolver;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.util.MapReferenceResolver;

import definitions.ClassInfo;

public class KryoSerializer {
	
	private final NoTypeSerializer noTypeSer = new NoTypeSerializer();
	private final NullTypeSerializer nullTypeSer = new NullTypeSerializer();
	private final PrimitiveTypeSerializer primSer = new PrimitiveTypeSerializer();
	private final ConstTypeSerializer constSer = new ConstTypeSerializer();
	private final BoxedTypeSerializer boxedSer = new BoxedTypeSerializer();
	private final PolymorphicTypeSerializer polySer = new PolymorphicTypeSerializer();
	private final VariableSerializer varSer = new VariableSerializer();
	private final ClassInfoSerializer classSer = new ClassInfoSerializer();

	public void writeObject(String file, Object obj) {
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		Output out = null;
		try {
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			out = new Output(bos);

			Kryo kryo = new Kryo();
//			kryo.register(NoType.class, noTypeSer);
//			kryo.register(NullType.class,nullTypeSer);
//			kryo.register(PrimitiveType.class, primSer);
//			kryo.register(ConstType.class, constSer);
//			kryo.register(BoxedType.class, boxedSer);
//			kryo.register(PolymorphicType.class, polySer);
//			kryo.register(Variable.class, varSer);
			//kryo.register(ClassInfo.class, classSer);
			
			kryo.writeObject(out, obj);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if (out != null) {
				
				try {
					if (fos != null){
						fos.flush();
						//fos.close();
					}
					
					if (bos != null) {
						bos.flush();
						//bos.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				out.flush();
				out.close();
			}
		}
	}
}
