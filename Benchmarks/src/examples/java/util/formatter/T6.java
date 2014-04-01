package examples.java.util.formatter;
import java.util.Formatter;

public class T6 {
  public static void main(String args[]) {
    Formatter fmt = new Formatter();
    // Right justify by default 
    fmt.format("|%10.2f|", 123.123); 
    System.out.println(fmt); 
  }
}