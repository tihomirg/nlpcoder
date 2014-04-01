package examples.java.util.formatter;
import java.util.Calendar;
import java.util.Formatter;

public class T16 {
  public static void main(String args[]) {
    Formatter fmt = new Formatter();
    Calendar cal = Calendar.getInstance();

    fmt.format("%tr", cal);
    System.out.println(fmt);

  }
}