package examples.java.io.printwriter;
import java.io.FileWriter;
import java.io.PrintWriter;

public class T4 {
  public static void main() throws Exception{
    PrintWriter pw = new PrintWriter(new FileWriter("dice.txt"));
    for (int i = 1; i <= 1000; i++) {
      int die = (int) (1 + 6 * Math.random());
      pw.print(die);
      pw.print(' ');
      if (i % 20 == 0)
        pw.println();
    }
    pw.println();
    pw.close(); // Without this, the output file may be empty
  }
}
