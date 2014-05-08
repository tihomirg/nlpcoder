package serializers.types;

import types.BoxedType;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import definitions.ClassInfo;

public class BoxedTypeSerializer extends Serializer<BoxedType>{

	@Override
	public BoxedType read(Kryo arg0, Input arg1, Class<BoxedType> arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void write(Kryo kryo, Output out, BoxedType boxed) {
		out.writeString(boxed.getPrefix());
		kryo.writeObjectOrNull(out, boxed.getClassInfo(), ClassInfo.class);
	}
}
