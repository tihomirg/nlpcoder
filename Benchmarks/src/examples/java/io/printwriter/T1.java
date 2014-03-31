package examples.java.io.printwriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

public class T1 {

  public static void main(String args[]) {

    try {

      // Create a print writer
      FileWriter fw = new FileWriter(args[0]);
      BufferedWriter bw = new BufferedWriter(fw);
      PrintWriter pw = new PrintWriter(bw, false);

      // Experiment with some methods
      pw.println(true);
      pw.println('A');
      pw.println(500);
      pw.println(40000L);
      pw.println(45.67f);
      pw.println(45.67);
      pw.println("Hello");
      pw.println(new Integer("99"));

      // Close print writer
      pw.close();
    } catch (Exception e) {
      System.out.println("Exception: " + e);
    }
  }
}