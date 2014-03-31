package examples.java.io.bytearrayinputstream;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

public class T2 {
  public static void main(String[] args) throws IOException {

    try {
      DataInputStream in3 = new DataInputStream(
        new ByteArrayInputStream("a dbcde".getBytes()));
      while(true)
        System.out.print((char)in3.readByte());
    } catch(EOFException e) {
      System.err.println("End of stream");
    }


  }
}
