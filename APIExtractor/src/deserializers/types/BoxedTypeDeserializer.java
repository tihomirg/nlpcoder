package deserializers.types;

import types.BoxedType;
import types.StabileTypeFactory;

import api.StabileAPI;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import definitions.ClassInfo;

public class BoxedTypeDeserializer extends Serializer<BoxedType>{

	private StabileTypeFactory factory;
	
	public BoxedTypeDeserializer(StabileTypeFactory factory) {
		this.factory = factory;
	}

	@Override
	public BoxedType read(Kryo kryo, Input in, Class<BoxedType> cls) {
		String name = in.readString();
		ClassInfo clazz = kryo.readObjectOrNull(in, ClassInfo.class);
		return factory.createBoxedType(name, clazz);
	}

	@Override
	public void write(Kryo arg0, Output arg1, BoxedType arg2) {
		throw new UnsupportedOperationException();		
	}

}