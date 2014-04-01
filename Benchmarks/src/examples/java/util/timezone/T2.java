package examples.java.util.timezone;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class T2 {
  public static void main(String[] argv) throws Exception {

    // Get the current time in Hong Kong
    Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("Hongkong"));

    int hour12 = cal.get(Calendar.HOUR); // 0..11
    int minutes = cal.get(Calendar.MINUTE); // 0..59
    int seconds = cal.get(Calendar.SECOND); // 0..59
    boolean am = cal.get(Calendar.AM_PM) == Calendar.AM;

    // Get the current hour-of-day at GMT
    cal.setTimeZone(TimeZone.getTimeZone("GMT"));
    int hour24 = cal.get(Calendar.HOUR_OF_DAY); // 0..23

    // Get the current local hour-of-day
    cal.setTimeZone(TimeZone.getDefault());
    hour24 = cal.get(Calendar.HOUR_OF_DAY); // 0..23
  }
}