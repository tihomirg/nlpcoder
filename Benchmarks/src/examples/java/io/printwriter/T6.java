package examples.java.io.printwriter;
import java.io.IOException;
import java.io.PrintWriter;

public class T6 {
  public static void main(String[] args) {
    try {
      PrintWriter pw = new PrintWriter("c:\\temp\\printWriterOutput.txt");
      pw.println("PrintWriter is easy to use.");
      pw.println(1234);
      pw.close();
    } catch (IOException e) {
    }
  }
}