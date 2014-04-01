package examples.java.util.formatter;
import java.util.Calendar;
import java.util.Formatter;

public class T13 {
  public static void main(String args[]) {
    Formatter fmt = new Formatter();
    Calendar cal = Calendar.getInstance();

    fmt = new Formatter();
    fmt.format("%tc", cal);
    System.out.println(fmt);

  }
}