package examples.java.util.formatter;
import java.util.Calendar;
import java.util.Formatter;

public class T14 {
  public static void main(String args[]) {
    Formatter fmt = new Formatter();
    Calendar cal = Calendar.getInstance();

    fmt.format("Today is day %te of %<tB, %<tY", cal);
    System.out.println(fmt);
  }
}