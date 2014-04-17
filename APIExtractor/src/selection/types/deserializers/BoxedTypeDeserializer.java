package selection.types.deserializers;

import selection.types.BoxedType;
import selection.types.StabileTypeFactory;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import definitions.ClassInfo;
import definitions.StabileClassInfoFactory;

public class BoxedTypeDeserializer extends Serializer<BoxedType>{

	private StabileTypeFactory factory;
	private StabileClassInfoFactory cif;
	
	public BoxedTypeDeserializer(StabileTypeFactory factory, StabileClassInfoFactory cif) {
		this.factory = factory;
		this.cif = cif;
	}

	@Override
	public BoxedType read(Kryo kryo, Input in, Class<BoxedType> cls) {
		String name = in.readString();
		ClassInfo clazz = cif.createClassInfo(kryo.readObjectOrNull(in, ClassInfo.class));
		return factory.createBoxedType(name, clazz);
	}

	@Override
	public void write(Kryo arg0, Output arg1, BoxedType arg2) {
		throw new UnsupportedOperationException();		
	}

}
