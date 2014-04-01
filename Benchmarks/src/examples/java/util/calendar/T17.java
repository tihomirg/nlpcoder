package examples.java.util.calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class T17 {
  public static void main(String[] args) {
    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");

    long now = System.currentTimeMillis();

    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(now);

    System.out.println(now + " = " + formatter.format(calendar.getTime()));
  }
} 