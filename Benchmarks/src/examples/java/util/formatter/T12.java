package examples.java.util.formatter;
import java.util.Calendar;
import java.util.Formatter;

public class T12 {
  public static void main(String args[]) {
    Formatter fmt = new Formatter();
    Calendar cal = Calendar.getInstance();

    // Display month by name and number. 
    fmt = new Formatter(); 
    fmt.format("%tB %tb %tm", cal, cal, cal); 
    System.out.println(fmt); 
  }
}