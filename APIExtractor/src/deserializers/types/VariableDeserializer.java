package deserializers.types;

import types.StabileTypeFactory;
import types.Variable;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class VariableDeserializer extends Serializer<Variable>{

	private StabileTypeFactory factory;
	
	public VariableDeserializer(StabileTypeFactory factory) {
		this.factory = factory;
	}

	@Override
	public Variable read(Kryo arg0, Input in, Class<Variable> arg2) {
		String name = in.readString();
		return factory.createVariable(name);
	}

	@Override
	public void write(Kryo arg0, Output arg1, Variable arg2) {
		throw new UnsupportedOperationException();
	}

}
