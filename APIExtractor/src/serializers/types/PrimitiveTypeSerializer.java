package serializers.types;

import types.PrimitiveType;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class PrimitiveTypeSerializer extends Serializer<PrimitiveType>{

	@Override
	public PrimitiveType read(Kryo arg0, Input arg1, Class<PrimitiveType> arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void write(Kryo arg0, Output out, PrimitiveType type) {
		out.writeString(type.getPrefix());
	}

}
