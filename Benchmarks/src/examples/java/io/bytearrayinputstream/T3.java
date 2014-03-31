package examples.java.io.bytearrayinputstream;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class T3 {
  public static void main(String args[]) throws IOException {
    String tmp = "abcdefghijklmnopqrstuvwxyz";
    byte b[] = tmp.getBytes();
    ByteArrayInputStream input1 = new ByteArrayInputStream(b);
    ByteArrayInputStream input2 = new ByteArrayInputStream(b, 0, 3);
  }
}
