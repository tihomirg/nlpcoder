package selection.types.deserializers;

import selection.types.ConstType;
import selection.types.StabileTypeFactory;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import definitions.ClassInfo;
import definitions.StabileClassInfoFactory;

public class ConstTypeDeserializer extends Serializer<ConstType>{

	private StabileTypeFactory factory;
	private StabileClassInfoFactory cif;
	
	public ConstTypeDeserializer(StabileTypeFactory factory, StabileClassInfoFactory cif) {
		this.factory = factory;
		this.cif = cif;
	}

	@Override
	public ConstType read(Kryo kryo, Input in, Class<ConstType> arg2) {
		String name = in.readString();
		ClassInfo clazz = cif.createClassInfo(kryo.readObjectOrNull(in, ClassInfo.class));
		return factory.createConstType(name, clazz);
	}

	@Override
	public void write(Kryo arg0, Output arg1, ConstType arg2) {
		throw new UnsupportedOperationException();
	}
}
