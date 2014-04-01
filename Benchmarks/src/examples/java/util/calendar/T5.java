package examples.java.util.calendar;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class T5 {

  public static void main(String args[]) {

    Calendar calendar1 = Calendar.getInstance();
    int doy1 = calendar1.get(Calendar.DAY_OF_YEAR);

    int year = calendar1.get(Calendar.YEAR);

    Calendar calendar2 = new GregorianCalendar(year, 11, 31);
    int doy2 = calendar2.get(Calendar.DAY_OF_YEAR);

    int days = doy2 - doy1;
    System.out.println(days + " days remain in current year");
  }
} 