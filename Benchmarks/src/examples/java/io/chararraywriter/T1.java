package examples.java.io.chararraywriter;
import java.io.CharArrayWriter;
import java.io.FileWriter;
import java.io.IOException;

public class T1 {
  public static void main(String args[]) throws IOException {
    CharArrayWriter f = new CharArrayWriter();
    String s = "This should end up in the array";
    char buf[] = new char[s.length()];
    s.getChars(0, s.length(), buf, 0);
    f.write(buf);
    System.out.println(f.toString());

    char c[] = f.toCharArray();
    for (int i = 0; i < c.length; i++) {
      System.out.print(c[i]);
    }

    FileWriter f2 = new FileWriter("test.txt");
    f.writeTo(f2);
    f2.close();
    f.reset();
    for (int i = 0; i < 3; i++)
      f.write('X');
  }
}
