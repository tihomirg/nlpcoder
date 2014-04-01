package examples.java.util.calendar;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class T8 {
  public static void main(String[] a) {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
    int day = calendar.get(Calendar.DAY_OF_WEEK);
    switch (day) {
    case Calendar.MONDAY:
      System.out.println(Calendar.MONDAY);
      break;
    case Calendar.TUESDAY:
      System.out.println(Calendar.TUESDAY);
      break;
    default:  
      System.out.println("others");
    }
  }
}
