package examples.java.util.formatter;
import java.util.Formatter;

public class T2 {
  public static void main(String args[]) {
    Formatter fmt = new Formatter();
    // Display at most 15 characters in a string.
    fmt = new Formatter();
    fmt.format("%.15s", "Formatting with Java is now easy.");
    System.out.println(fmt);
  }
}