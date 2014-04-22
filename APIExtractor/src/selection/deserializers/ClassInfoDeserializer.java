package selection.deserializers;

import selection.types.ConstType;
import selection.types.ReferenceType;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import definitions.ClassInfo;
import definitions.Declaration;

public class ClassInfoDeserializer extends Serializer<ClassInfo>{

	@Override
	public ClassInfo read(Kryo kryo, Input in, Class<ClassInfo> arg2) {
		ClassInfo clazz = new ClassInfo();
		
		clazz.setName(in.readString());
		clazz.setPackageName(in.readString());
		clazz.setSimpleName(in.readString());
		
		clazz.setPublic(in.readBoolean());
		clazz.setClass(in.readBoolean());
		
		Registration type = kryo.readClass(in);
		
		clazz.setType((ReferenceType) kryo.readObject(in, type.getType()));
		clazz.setMethods(kryo.readObject(in, Declaration[].class));
		clazz.setFields(kryo.readObject(in, Declaration[].class));
		clazz.setInheritedTypes(kryo.readObject(in, ReferenceType[].class));
		clazz.setSuperClasses(kryo.readObject(in, ClassInfo[].class));
		clazz.setInterfaces(kryo.readObject(in, ClassInfo[].class));
		
		return clazz;
	}

	@Override
	public void write(Kryo arg0, Output arg1, ClassInfo arg2) {
		throw new UnsupportedOperationException();
	}

}
