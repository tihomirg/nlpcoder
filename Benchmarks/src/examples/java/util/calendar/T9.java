package examples.java.util.calendar;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class T9 {
  public static void main(String[] a) {
    GregorianCalendar today = new GregorianCalendar();
    GregorianCalendar thisDate = new GregorianCalendar();
    thisDate.set(Calendar.YEAR, 2000);
    if (thisDate.before(today)) {
      System.out.println("before");
    }
    if (today.after(thisDate)) {
      System.out.println("after");
    }
  }
}