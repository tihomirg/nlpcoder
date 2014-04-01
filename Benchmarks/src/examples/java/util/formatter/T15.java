package examples.java.util.formatter;
import java.util.Calendar;
import java.util.Formatter;

public class T15 {
  public static void main(String args[]) {
    Formatter fmt = new Formatter();
    Calendar cal = Calendar.getInstance();

    // Display just hour and minute. 
    fmt = new Formatter(); 
    fmt.format("%tl:%tM", cal, cal); 
    System.out.println(fmt); 

  }
}