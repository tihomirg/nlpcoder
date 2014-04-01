package examples.java.util.calendar;
import java.util.Calendar;
import java.util.Date;

public class T3 {
  public static void main(String args[]) {
    Calendar cal = Calendar.getInstance();
    Date now = new Date();
    cal.setTime(now);
    int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
    if (dayofweek == Calendar.SUNDAY)
      System.out.println("SUNDAY");
    if (dayofweek == Calendar.MONDAY)
      System.out.println("MONDAY");
    if (dayofweek == Calendar.TUESDAY)
      System.out.println("TUESDAY");
    if (dayofweek == Calendar.WEDNESDAY)
      System.out.println("WEDNESDAY");
    if (dayofweek == Calendar.THURSDAY)
      System.out.println("THURSDAY");
    if (dayofweek == Calendar.FRIDAY)
      System.out.println("FRIDAY");
    if (dayofweek == Calendar.SATURDAY) {
      System.out.println("SATURDAY");
    }
  }
}