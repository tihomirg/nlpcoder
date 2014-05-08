package serializers.types;

import types.Variable;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class VariableSerializer extends Serializer<Variable> {

	@Override
	public Variable read(Kryo kryo, Input in, Class<Variable> type) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void write(Kryo kryo, Output out, Variable var) {
		out.writeString(var.getPrefix());
	}

}
