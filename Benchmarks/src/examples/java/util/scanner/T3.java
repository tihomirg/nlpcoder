package examples.java.util.scanner;
import java.util.Scanner;

public class T3 {
  public static void main(String args[]) {
    String instr = "Name: Joe Age: 28 ID: 77";

    Scanner conin = new Scanner(instr);

    conin.findInLine("Age:"); // find Age

    if (conin.hasNext())
      System.out.println(conin.next());
    else
      System.out.println("Error!");

  }
}