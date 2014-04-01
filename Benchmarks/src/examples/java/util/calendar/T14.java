package examples.java.util.calendar;
import java.util.Calendar;

public class T14 {
  public static void main(String[] args) {
    int year = 2009;
    int month = 0; // January
    int date = 1;

    Calendar cal = Calendar.getInstance();
    cal.clear();

    cal.set(Calendar.YEAR, year);
    cal.set(Calendar.MONTH, month);
    cal.set(Calendar.DATE, date);

    java.util.Date utilDate = cal.getTime();
    System.out.println(utilDate);
  }
}