import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.ByteBufferOutputStream;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.CollectionSerializer;

import definitions.Declaration;


public class KryoMain {

	public static void main(String[] args){

		String file = "jars.txt";
		//listExample(file);
		declExample(file);

	}

	private static void listExample(String file) {
		List<String> myList = new ArrayList<String>();
		myList.add("apples");
		myList.add("bananas");		

		CollectionSerializer serializer = new CollectionSerializer(String.class, null);		

		Class type = ArrayList.class;

		writeObject(file, myList, serializer, type);
		
		List<String> list2 = (ArrayList<String>) readObject(file, serializer, type);
		System.out.println(Arrays.toString(list2.toArray()));
	}
	
	public static void declExample(String file){
		final Declaration decl = new Declaration();
		decl.setArgNum(2);
		decl.setName("testMethod");
		decl.setClazz("TestClass");
		decl.setMethod(true);
		
		List<Declaration> myList = new ArrayList<Declaration>();
		
		myList.add(decl);
		myList.add(decl);
		
		CollectionSerializer serializer = new CollectionSerializer(Declaration.class, null);	
		
		Class type = ArrayList.class;

		writeObject(file, myList, serializer, type);
		
		List<Declaration> myList2 = (ArrayList<Declaration>) readObject(file, serializer, type);
		System.out.println(Arrays.toString(myList2.toArray()));	
	}

	public static Object readObject(String file,
			Serializer serializer, Class type) {
		
		Object obj = null;
		
		try {
			Input in = new Input(new BufferedInputStream(new FileInputStream(file)));			

			Kryo kryo = new Kryo();	
			kryo.register(type, serializer);
			obj = kryo.readObject(in, type);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	public static void writeObject(String file, Object myList,
			Serializer serializer, Class type) {
		try {
			Output out = new Output(new BufferedOutputStream(new FileOutputStream(file)));

			Kryo kryo = new Kryo();
			
			kryo.register(type, serializer);
			kryo.writeObject(out, myList);

			out.flush();
			out.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
