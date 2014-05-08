package serializers.types;

import types.ConstType;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import definitions.ClassInfo;

public class ConstTypeSerializer extends Serializer<ConstType> {

	@Override
	public ConstType read(Kryo arg0, Input arg1, Class<ConstType> arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void write(Kryo kryo, Output out, ConstType cons) {
		out.writeString(cons.getPrefix());
		kryo.writeObjectOrNull(out, cons.getClassInfo(), ClassInfo.class);
	}

}
