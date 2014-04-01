package examples.java.util.locale;
import java.util.Locale;

/** Print the default locale */
public class T5 {

  public static void main(String[] av) {
    Locale l = Locale.getDefault();
    System.out.println("Today's Locale is " + l);
  }
}