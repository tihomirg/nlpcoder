package examples.java.util.gregoriancalendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class T1 {
  public static void main(String[] a) {
    GregorianCalendar calendar = new GregorianCalendar();
    Date now = calendar.getTime();
    System.out.println(now);
  }
}