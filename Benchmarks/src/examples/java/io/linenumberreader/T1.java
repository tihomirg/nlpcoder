package examples.java.io.linenumberreader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class T1 {

  public static void main(String[] args) throws Exception {
    LineNumberReader lineCounter = new LineNumberReader(new InputStreamReader(System.in));

    String nextLine = null;
    System.out.println("Type any text and press return. Type 'exit' to quit the program.");
    try {
      while ((nextLine = lineCounter.readLine()).indexOf("exit") == -1) {
        if (nextLine == null)
          break;
        System.out.print(lineCounter.getLineNumber());
        System.out.print(": ");
        System.out.println(nextLine);
      }
    } catch (Exception done) {
      done.printStackTrace();
    }
  }
}