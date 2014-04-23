package selection.serializers;

import selection.types.ReferenceType;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import definitions.ClassInfo;

public class ClassInfoSerializer extends com.esotericsoftware.kryo.Serializer<ClassInfo>{

	@Override
	public ClassInfo read(Kryo arg0, Input arg1, Class<ClassInfo> arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void write(Kryo kryo, Output out, ClassInfo clazz) {
		out.writeString(clazz.getName());
		out.writeString(clazz.getPackageName());
		out.writeString(clazz.getSimpleName());
		out.writeBoolean(clazz.isPublic());
		out.writeBoolean(clazz.isClass());
	
		ReferenceType type = clazz.getType();
		kryo.writeClass(out, type.getClass());
		
		if (clazz.getName().equals(java.lang.Number.class.getName())){
			System.out.println("***************************************  "+type +"   "+type.getClass());
		}
		
		kryo.writeObject(out, type);
		kryo.writeObject(out, clazz.getMethods());
		kryo.writeObject(out, clazz.getFields());
		kryo.writeObject(out, clazz.getInheritedTypes());
		kryo.writeObject(out, clazz.getSuperClasses());
		kryo.writeObject(out, clazz.getInterfaces());
	}

}
