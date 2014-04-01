package examples.java.util.formatter;
import java.util.Formatter;

public class T4 {
  public static void main(String args[]) {
    Formatter fmt = new Formatter();
    
    fmt.format("%4d %4d %4d", 1, 2, 3); 
    System.out.println(fmt); 
  }
}
    