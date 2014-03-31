package examples.java.io.stringreader;
import java.io.IOException;
import java.io.StringReader;

public class T1 {
  public static void main(String[] args) throws IOException {


    StringReader in2 = new StringReader("a bc ddd");
    int c;
    while ((c = in2.read()) != -1)
      System.out.print((char) c);

  }
}//