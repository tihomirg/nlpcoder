package examples.java.io.pipedreader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class T1 {
  public static void main(String[] args) throws IOException {
    PipedWriter pw = new PipedWriter();
    PipedReader pr = new PipedReader(pw);
    int ch;
    try {
      for (int i = 0; i < 15; i++)
        pw.write(" A" + i + '\n');
      while ((ch = pr.read()) != -1)
        System.out.print((char) ch);
    } catch (IOException e) {
    }
  }
}