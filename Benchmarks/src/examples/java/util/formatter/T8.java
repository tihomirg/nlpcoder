package examples.java.util.formatter;
import java.util.Formatter;

public class T8 {
  public static void main(String args[]) {
    Formatter fmt = new Formatter();
    // left justify. 
    fmt.format("|%-10.2f|", 123.123); 
    System.out.println(fmt); 
  }
}