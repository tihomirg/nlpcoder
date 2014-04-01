package examples.java.util.calendar;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

public class T13 {

  public static void main(String[] argv) {
    Calendar JO_cal = Calendar.getInstance(new Locale("ar", "JO"));
    Calendar FR_cal = Calendar.getInstance(Locale.FRANCE);
    Calendar CA_cal = Calendar.getInstance(Locale.CANADA);

    DateFormatSymbols dfs = new DateFormatSymbols();
    String weekdays[] = dfs.getWeekdays();

    System.out.println(weekdays[JO_cal.getFirstDayOfWeek()]);
    System.out.println(weekdays[FR_cal.getFirstDayOfWeek()]);
    System.out.println(weekdays[CA_cal.getFirstDayOfWeek()]);

  }
}