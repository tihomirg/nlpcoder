package examples.java.io.bytearrayoutputstream;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class T1 {
  public static void main(String args[]) throws Exception {
    ByteArrayOutputStream f = new ByteArrayOutputStream(12);
    System.out.println("Please 10 characters and a return");
    while (f.size() != 10) {
      f.write(System.in.read());
    }
    System.out.println("Buffer as a string");
    System.out.println(f.toString());
    System.out.println("Into array");
    byte b[] = f.toByteArray();
    for (int i = 0; i < b.length; i++) {
      System.out.print((char) b[i]);
    }
    System.out.println();
    OutputStream f2 = new FileOutputStream("test.txt");
    f.writeTo(f2);
    f.reset();
    System.out.println("10 characters and a return");
    while (f.size() != 10) {
      f.write(System.in.read());
    }
    System.out.println("Done..");
  }
}