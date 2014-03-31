package examples.java.io.chararrayreader;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.IOException;

public class T1 {
  public static void main(String args[]) throws IOException {
    CharArrayWriter outStream = new CharArrayWriter();
    String s = "This is a test.";
    for (int i = 0; i < s.length(); ++i)
      outStream.write(s.charAt(i));
    System.out.println("outstream: " + outStream);
    System.out.println("size: " + outStream.size());
    CharArrayReader inStream;
    inStream = new CharArrayReader(outStream.toCharArray());
    int ch = 0;
    StringBuffer sb = new StringBuffer("");
    while ((ch = inStream.read()) != -1)
      sb.append((char) ch);
    s = sb.toString();
    System.out.println(s.length() + " characters were read");
    System.out.println("They are: " + s);
  }
}