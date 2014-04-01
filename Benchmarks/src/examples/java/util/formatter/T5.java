package examples.java.util.formatter;
import java.util.Formatter;

public class T5 {
  public static void main(String args[]) {
    Formatter fmt = new Formatter();
    // Format to 2 decimal places in a 16 character field. 
    fmt = new Formatter(); 
    fmt.format("%16.2e", 123.1234567); 
    System.out.println(fmt); 
  }
}