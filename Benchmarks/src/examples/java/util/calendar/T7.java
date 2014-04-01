package examples.java.util.calendar;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class T7 {

  public static void main(String[] a) throws Exception {

    Calendar now = Calendar.getInstance();

    SimpleDateFormat formatter = new SimpleDateFormat("E yyyy/MM/dd 'at' hh:mm:ss a zzz");
    System.out.println("It is now " + formatter.format(now.getTime()));

    now.add(Calendar.YEAR, -2);
    System.out.println("Two years ago was " + formatter.format(now.getTime()));
  }
}