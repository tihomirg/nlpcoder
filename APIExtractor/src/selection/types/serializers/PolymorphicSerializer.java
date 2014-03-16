package selection.types.serializers;

import selection.types.Polymorphic;
import selection.types.Type;
import selection.types.TypeFactory;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class PolymorphicSerializer extends Serializer<Polymorphic> {

	private TypeFactory factory;
	
	public PolymorphicSerializer(TypeFactory factory) {
		this.factory = factory;
	}

	@Override
	public Polymorphic read(Kryo kryo, Input in, Class<Polymorphic> type) {
		String name = in.readString();
		Type[] params = kryo.readObject(in, Type[].class);
		return factory.createPolymorphic(name, params);
	}

	@Override
	public void write(Kryo kryo, Output out, Polymorphic poly) {
		out.writeString(poly.getName());
		kryo.writeObject(out, poly.getParams());
	}

}
