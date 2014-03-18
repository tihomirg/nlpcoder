package selection.serializers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import selection.Config;
import selection.types.Const;
import selection.types.NameGenerator;
import selection.types.Polymorphic;
import selection.types.Type;
import selection.types.TypeFactory;
import selection.types.Variable;
import selection.types.serializers.ConstSerializer;
import selection.types.serializers.PolymorphicSerializer;
import selection.types.serializers.VariableSrializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class TypeSerializer {
	
	private TypeFactory factory;
	private Serializer<Const> consSer;
	private Serializer<Polymorphic> polySer;
	private Serializer<Variable> varSer;
	
	public TypeSerializer(TypeFactory factory) {
		this.factory = factory;
		consSer = new ConstSerializer(factory);
		polySer = new PolymorphicSerializer(factory);
		varSer = new VariableSrializer(factory);		
	}

	public Object readObject(String file, Class type) {
		
		Object obj = null;
		
		try {
			Input in = new Input(new BufferedInputStream(new FileInputStream(file)));			

			Kryo kryo = new Kryo();
			kryo.register(Const.class, consSer);
			kryo.register(Polymorphic.class, polySer);
			kryo.register(Variable.class, varSer);
			
			obj = kryo.readObject(in, type);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	public void writeObject(String file, Object obj) {
		try {
			Output out = new Output(new BufferedOutputStream(new FileOutputStream(file)));

			Kryo kryo = new Kryo();
			kryo.register(Const.class, consSer);
			kryo.register(Polymorphic.class, polySer);
			kryo.register(Variable.class, varSer);
			
			kryo.writeObject(out, obj);

			out.flush();
			out.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		TypeFactory factory = new TypeFactory(new NameGenerator(Config.getSerializationVariablePrefix()));
		TypeSerializer serializer = new TypeSerializer(factory);
		
		String file = "types.kryo";
		
//		serializer.writeObject(file, factory.createConst("java.lang.Integer"));
//		Const type = (Const) serializer.readObject(file, Const.class);
		
		Const cons1 = factory.createConst("java.lang.Integer");
		Const cons2 = factory.createConst("java.lang.String");
		
		Polymorphic poly = factory.createPolymorphic("java.util.List", new Type[]{cons1});
		Polymorphic poly2 = factory.createPolymorphic("java.util.Map", new Type[]{poly, cons2});
		
		serializer.writeObject(file, poly2);
		Object type = serializer.readObject(file, poly2.getClass());
		
		System.out.println(type);
		System.out.println();
		System.out.println(factory);
	}
}
