package examples.java.util.timezone;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class T4 {
  public static void main(String[] a) {
    GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("America/Chicago"),
        Locale.US);
    System.out.println(calendar);
  }
}