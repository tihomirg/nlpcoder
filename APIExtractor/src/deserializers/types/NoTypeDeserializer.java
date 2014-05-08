package deserializers.types;

import types.NoType;
import types.StabileTypeFactory;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class NoTypeDeserializer extends Serializer<NoType>{

	private StabileTypeFactory factory;
	
	public NoTypeDeserializer(StabileTypeFactory factory) {
		this.factory = factory;
	}

	@Override
	public NoType read(Kryo arg0, Input in, Class<NoType> arg2) {
		in.readString();
		return factory.createNoType();
	}

	@Override
	public void write(Kryo arg0, Output arg1, NoType arg2) {
		throw new UnsupportedOperationException();
	}

}
