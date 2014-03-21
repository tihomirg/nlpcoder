package selection.types.serializers;

import selection.types.Const;
import selection.types.TypeFactory;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class ConstSerializer extends Serializer<Const> {


	private TypeFactory factory;	
	
	public ConstSerializer(TypeFactory factory) {
		this.factory = factory;
	}
	
	@Override
	public Const read(Kryo kryo, Input in, Class<Const> type) {
		String name = in.readString();
		return factory.createConst(name);
	}

	@Override
	public void write(Kryo kryo, Output out, Const cons) {
		out.writeString(cons.getHead());
	}
}
