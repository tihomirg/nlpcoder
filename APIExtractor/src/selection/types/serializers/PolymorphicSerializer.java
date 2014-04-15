package selection.types.serializers;

import selection.types.PolymorphicType;
import selection.types.Type;
import selection.types.TypeFactory;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import definitions.ClassInfo;

public class PolymorphicSerializer extends Serializer<PolymorphicType> {

	private TypeFactory factory;
	
	public PolymorphicSerializer(TypeFactory factory) {
		this.factory = factory;
	}

	@Override
	public PolymorphicType read(Kryo kryo, Input in, Class<PolymorphicType> type) {
		ClassInfo clazz = kryo.readObject(in, ClassInfo.class);
		Type[] params = kryo.readObject(in, Type[].class);
		return factory.createPolymorphicType(clazz, params);
	}

	@Override
	public void write(Kryo kryo, Output out, PolymorphicType poly) {
		kryo.writeObject(out, poly.getClassInfo());
		kryo.writeObject(out, poly.getParams());
	}

}
