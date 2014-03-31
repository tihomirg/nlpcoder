package examples.java.io.bytearrayinputstream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class T1 {
  public static void main(String args[]) throws IOException {
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    String s = "This is a test.";
    for (int i = 0; i < s.length(); ++i)
      outStream.write(s.charAt(i));
    System.out.println("outstream: " + outStream);
    System.out.println("size: " + outStream.size());
    ByteArrayInputStream inStream = new ByteArrayInputStream(outStream.toByteArray());
    int inBytes = inStream.available();
    System.out.println("inStream has " + inBytes + " available bytes");
    byte inBuf[] = new byte[inBytes];
    int bytesRead = inStream.read(inBuf, 0, inBytes);
    System.out.println(bytesRead + " bytes were read");
    System.out.println("They are: " + new String(inBuf));
  }
}