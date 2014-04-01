package examples.java.util.formatter;
import java.util.Formatter;

public class T9 {
  public static void main(String args[]) {
    Formatter fmt = new Formatter();
    fmt.format("% d", -100); 
    System.out.println(fmt); 
 
    fmt = new Formatter(); 
    fmt.format("% d", 100); 
    System.out.println(fmt); 
 
    fmt = new Formatter(); 
    fmt.format("% d", -200); 
    System.out.println(fmt); 
 
    fmt = new Formatter(); 
    fmt.format("% d", 200); 
    System.out.println(fmt); 
  }
}