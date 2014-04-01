package examples.java.util.locale;
import java.util.Locale;

public class T7 {

  public static void main(String [] argv) {

    Locale defaultLocale = Locale.getDefault();
    System.out.println(defaultLocale.getISO3Country());
    System.out.println(defaultLocale.getDisplayCountry());
    System.out.println(defaultLocale.getDisplayCountry(Locale.GERMAN));
  }
}