package examples.java.util.timezone;
import java.util.Calendar;
import java.util.TimeZone;

public class T3 {

  public static void main(String[] args) {
    Calendar now = Calendar.getInstance();
    TimeZone timeZone = now.getTimeZone();
    System.out.println("Current TimeZone is : " + timeZone.getDisplayName());
  }
}