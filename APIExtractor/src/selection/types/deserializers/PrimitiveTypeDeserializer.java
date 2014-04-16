package selection.types.deserializers;

import selection.types.PrimitiveType;
import selection.types.StabileTypeFactory;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class PrimitiveTypeDeserializer extends Serializer<PrimitiveType>{

	private StabileTypeFactory factory;

	public PrimitiveTypeDeserializer(StabileTypeFactory factory) {
		this.factory = factory;
	}
	
	@Override
	public PrimitiveType read(Kryo arg0, Input in, Class<PrimitiveType> arg2) {
		String name = in.readString();
		return factory.createPrimitiveType(name);
	}

	@Override
	public void write(Kryo arg0, Output arg1, PrimitiveType arg2) {
		throw new UnsupportedOperationException();
	}

}
