package examples.java.util.calendar;
import java.util.Calendar;

public class T11 {
  public static void main(String[] argv) throws Exception {
    Calendar cal = Calendar.getInstance();
    int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 28
  }
}