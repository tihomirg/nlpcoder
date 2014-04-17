package selection.types.deserializers;

import selection.types.PolymorphicType;
import selection.types.StabileTypeFactory;
import selection.types.Type;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import definitions.ClassInfo;
import definitions.StabileClassInfoFactory;

public class PolymorphicTypeDeserializer extends Serializer<PolymorphicType>{

	private StabileTypeFactory factory;
	private StabileClassInfoFactory cif;
	
	public PolymorphicTypeDeserializer(StabileTypeFactory factory, StabileClassInfoFactory cif) {
		this.factory = factory;
		this.cif = cif;
	}

	@Override
	public PolymorphicType read(Kryo kryo, Input in, Class<PolymorphicType> arg2) {
		String name = in.readString();
		ClassInfo clazz = cif.createClassInfo(kryo.readObjectOrNull(in, ClassInfo.class));
		Type[] params = kryo.readObject(in, Type[].class);
		return factory.createPolymorphicType(name, clazz, params);
	}

	@Override
	public void write(Kryo arg0, Output arg1, PolymorphicType arg2) {
		throw new UnsupportedOperationException();
	}
}
