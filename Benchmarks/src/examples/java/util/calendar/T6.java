package examples.java.util.calendar;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class T6 {

  public static void main(String[] a) {
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(new Date());

    System.out.println("ERA: " + calendar.get(Calendar.ERA));
    System.out.println("YEAR: " + calendar.get(Calendar.YEAR));
    System.out.println("MONTH: " + calendar.get(Calendar.MONTH));
    System.out.println("WEEK_OF_YEAR: " + calendar.get(Calendar.WEEK_OF_YEAR));
    System.out.println("WEEK_OF_MONTH: " + calendar.get(Calendar.WEEK_OF_MONTH));
    System.out.println("DATE: " + calendar.get(Calendar.DATE));
    System.out.println("DAY_OF_MONTH: " + calendar.get(Calendar.DAY_OF_MONTH));
    System.out.println("DAY_OF_WEEK: " + calendar.get(Calendar.DAY_OF_WEEK));
    System.out.println("DAY_OF_WEEK_IN_MONTH: " + calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
    System.out.println("AM_PM: " + calendar.get(Calendar.AM_PM));
    System.out.println("MILLISECOND: " + calendar.get(Calendar.MILLISECOND));
    System.out.println("ZONE_OFFSET: "+ (calendar.get(Calendar.ZONE_OFFSET)/(60*60*1000)));
    System.out.println("DST_OFFSET: "+ (calendar.get(Calendar.DST_OFFSET)/(60*60*1000)));
  }
}