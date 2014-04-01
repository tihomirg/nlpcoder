package examples.java.util.navigablemap;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class T2 {
  public static void main(String args[]) {
    Calendar now = Calendar.getInstance();
    Locale locale = Locale.getDefault();

    Map<String, Integer> names = now.getDisplayNames(Calendar.DAY_OF_WEEK, Calendar.LONG, locale);
    NavigableMap<String, Integer> nav = new TreeMap<String, Integer>(names);
    System.out.printf("Whole list:%n%s%n", nav);
    System.out.printf("Key floor before Sunday: %s%n", nav.floorKey("Sunday"));
  }
}