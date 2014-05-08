package serializers.types;

import types.NullType;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class NullTypeSerializer extends Serializer<NullType>{

	@Override
	public NullType read(Kryo arg0, Input arg1, Class<NullType> arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void write(Kryo arg0, Output out, NullType type) {
		out.writeString(type.getPrefix());
	}
}
