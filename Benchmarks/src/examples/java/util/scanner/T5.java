package examples.java.util.scanner;
import java.util.Scanner;

public class T5 {
  public static void main(String args[]) {
    Scanner sc = new Scanner("Name: Tom Age: 28 ID: 77");

    sc.findWithinHorizon("ID:",100);

    if (sc.hasNext())
      System.out.println(sc.next());
    else
      System.out.println("Error!");
  }
}