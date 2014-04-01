package examples.java.util.formatter;
import java.util.Formatter;

public class T3 {
  public static void main(String args[]) {
    Formatter fmt = new Formatter();
    // Format 4 decimal places. 
    fmt.format("%.4f", 123.1234567); 
    System.out.println(fmt); 
  }
}