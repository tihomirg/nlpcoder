package tests;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class KryoCycleTest {
	
	public static Object readObject(String file, Class type) {
		
		Object obj = null;
		
		try {
			Input in = new Input(new BufferedInputStream(new FileInputStream(file)));			

			Kryo kryo = new Kryo();	
			obj = kryo.readObject(in, type);
			
			in.close();			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	public static void writeObject(String file, Object myList, Class type) {
		try {
			Output out = new Output(new BufferedOutputStream(new FileOutputStream(file)));

			Kryo kryo = new Kryo();
			
			kryo.writeObject(out, myList);

			out.flush();
			out.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Test 1:");
		test1();
		
		System.out.println("Test 2:");
		test2();
		
	}

	private static void test2() {

		B b1 = new B();
		
		B b2 = new B();
		
		b1.b = b2;
		b2.b = b1;
		
		KryoCycleTest.writeObject("kct.kryo", b1, B.class);

		B b3 = (B) KryoCycleTest.readObject("kct.kryo", B.class);
		
		System.out.println(b3.hashCode());
		System.out.println(b3.b.hashCode());
		System.out.println(b3.b.b.hashCode());
		System.out.println(b3.b.b == b3);
		
	}

	private static void test1() {
		A a1 = new A();
		A a2 = new A();
		A a3 = a2;
		
		System.out.println("Before:");
		System.out.println(a1 == a2);
		System.out.println(a1 == a3);
		System.out.println(a2 == a3);
		
		System.out.println(a1.hashCode());
		System.out.println(a2.hashCode());
		System.out.println(a3.hashCode());		
		System.out.println();
				
		KryoCycleTest.writeObject("kct.kryo", new A[]{a1, a2, a3}, A[].class);

		A[] a = (A[]) KryoCycleTest.readObject("kct.kryo", A[].class);
		
		a1 = a[0];
		a2 = a[1];
		a3 = a[2];
		
		System.out.println("After:");
		System.out.println(a1 == a2);
		System.out.println(a1 == a3);
		System.out.println(a2 == a3);
		
		System.out.println(a1.hashCode());
		System.out.println(a2.hashCode());
		System.out.println(a3.hashCode());		
		System.out.println();
	}
}


class A{}

class B {
	
	public B b;
	
}