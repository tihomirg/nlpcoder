package selection.types.serializers;

import selection.types.PolymorphicType;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;


public class PolymorphicSerializer extends Serializer<PolymorphicType> {

	@Override
	public PolymorphicType read(Kryo kryo, Input in, Class<PolymorphicType> type) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void write(Kryo kryo, Output out, PolymorphicType poly) {
		out.writeString(poly.getPrefix());
		kryo.writeObjectOrNull(out, poly.getClassInfo(), poly.getClass());
		kryo.writeObject(out, poly.getParams());
	}
}
