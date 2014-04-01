package examples.java.util.gregoriancalendar;
import java.util.Calendar;
import java.util.GregorianCalendar;
public class T3 {
  public static void main(String[] a) {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.roll(Calendar.MONTH, false); // Go back a month
    System.out.println(calendar.get(Calendar.MONTH));
  }
}