package examples.java.util.gregoriancalendar;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class T2 {

  public static void main(String args[]) {
    GregorianCalendar gc = new GregorianCalendar();
    int year = gc.get(Calendar.YEAR);
    System.out.println(year);
    System.out.println(gc.isLeapYear(year));
    System.out.println("Month = " + gc.get(Calendar.MONTH));
    System.out.println("Week of year = " + gc.get(Calendar.WEEK_OF_YEAR));
    System.out.println("Week of month = " + gc.get(Calendar.WEEK_OF_MONTH));
    System.out.println("Day of year = " + gc.get(Calendar.DAY_OF_YEAR));
    System.out.println("Day of week = " + gc.get(Calendar.DAY_OF_WEEK));
  }
}