package examples.java.util.gregoriancalendar;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class T6 {

  public static void main(String args[]) {
    GregorianCalendar today = new GregorianCalendar();

    int todayMonth = today.get(Calendar.MONTH);

    int todayDayOfMonth = today.get(Calendar.DAY_OF_MONTH);

    int todayYear = today.get(Calendar.YEAR);

    int todayDayOfYear = today.get(Calendar.DAY_OF_YEAR);

    int numDays;
    if (todayMonth == 11 && todayDayOfMonth > 25) {
      GregorianCalendar xmas = new GregorianCalendar(todayYear + 1, 11, 25);
      int xmasDayOfYear = xmas.get(Calendar.DAY_OF_YEAR);
      numDays = xmasDayOfYear + 31 - todayDayOfMonth;
    } else {
      GregorianCalendar xmas = new GregorianCalendar(todayYear, 11, 25);
      int xmasDayOfYear = xmas.get(Calendar.DAY_OF_YEAR);
      numDays = xmasDayOfYear - todayDayOfYear;
    }

    System.out.println("The number of days till Christmas is " + numDays);
  }
}