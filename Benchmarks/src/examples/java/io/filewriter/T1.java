package examples.java.io.filewriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class T1 {
  public static void main(String[] args) {
    try {
      FileWriter fw = new FileWriter("LPT1:");

      PrintWriter pw = new PrintWriter(fw);
      String s = "www.java2s.com";

      int i, len = s.length();

      for (i = 0; len > 80; i += 80) {
        pw.print(s.substring(i, i + 80));
        pw.print("\r\n");
        len -= 80;
      }

      if (len > 0) {
        pw.print(s.substring(i));
        pw.print("\r\n");
      }

      pw.close();
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}