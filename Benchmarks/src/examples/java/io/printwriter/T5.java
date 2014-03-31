package examples.java.io.printwriter;

import java.io.PrintWriter;

public class T5 {

  public static void main(String args[]) throws Exception {

    PrintWriter pw = new PrintWriter(System.out);

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
  }
}
