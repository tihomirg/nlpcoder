package examples.java.io.chararrayreader;
 
import java.io.CharArrayReader;
import java.io.IOException;

public class T2 {
  public static void main(String args[]) throws IOException {
    String tmp = "abcdefghijklmnopqrstuvwxyz";
    int length = tmp.length();
    char c[] = new char[length];

    tmp.getChars(0, length, c, 0);
    CharArrayReader input1 = new CharArrayReader(c);
    CharArrayReader input2 = new CharArrayReader(c, 0, 5);

    int i;
    while ((i = input1.read()) != -1) {
      System.out.print((char) i);
    }

    while ((i = input2.read()) != -1) {
      System.out.print((char) i);
    }
  }
}