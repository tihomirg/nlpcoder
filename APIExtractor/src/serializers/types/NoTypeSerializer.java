package serializers.types;

import types.NoType;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class NoTypeSerializer extends Serializer<NoType>{

	@Override
	public NoType read(Kryo arg0, Input arg1, Class<NoType> arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void write(Kryo arg0, Output out, NoType arg2) {
		out.writeString(arg2.getPrefix());
	}
}
