package selection.types.serializers;

import selection.types.TypeFactory;
import selection.types.Variable;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class VariableSrializer extends Serializer<Variable> {

	private TypeFactory factory;
	
	public VariableSrializer(TypeFactory factory) {
		this.factory = factory;
	}

	@Override
	public Variable read(Kryo kryo, Input in, Class<Variable> type) {
		return factory.createVariable(in.readString());
	}

	@Override
	public void write(Kryo kryo, Output out, Variable var) {
		out.writeString(var.getPrefix());
	}

}
