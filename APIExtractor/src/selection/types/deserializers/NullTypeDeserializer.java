package selection.types.deserializers;

import selection.types.NullType;
import selection.types.StabileTypeFactory;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class NullTypeDeserializer extends Serializer<NullType>{

	private StabileTypeFactory factory;
	
	public NullTypeDeserializer(StabileTypeFactory factory) {
		this.factory = factory;
	}

	@Override
	public NullType read(Kryo arg0, Input in, Class<NullType> arg2) {
		in.readString();
		return factory.createNullType();
	}

	@Override
	public void write(Kryo arg0, Output arg1, NullType arg2) {
		throw new UnsupportedOperationException();		
	}

}
