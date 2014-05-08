package deserializers.types;

import types.PolymorphicType;
import types.ReferenceType;
import types.StabileTypeFactory;
import types.Type;

import api.StabileAPI;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import definitions.ClassInfo;

public class PolymorphicTypeDeserializer extends Serializer<PolymorphicType>{

	private StabileTypeFactory factory;
	
	public PolymorphicTypeDeserializer(StabileTypeFactory factory) {
		this.factory = factory;
	}

	@Override
	public PolymorphicType read(Kryo kryo, Input in, Class<PolymorphicType> arg2) {
		String name = in.readString();
		ClassInfo clazz = kryo.readObjectOrNull(in, ClassInfo.class);
		Type[] params = kryo.readObject(in, Type[].class);
		return factory.createPolymorphicType(name, clazz, params);
	}

	@Override
	public void write(Kryo arg0, Output arg1, PolymorphicType arg2) {
		throw new UnsupportedOperationException();
	}
}
