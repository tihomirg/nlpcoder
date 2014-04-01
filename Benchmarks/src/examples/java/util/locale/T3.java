package examples.java.util.locale;
import java.util.Locale;

public class T3 {
  public static void main(String args[]) throws Exception {
    Locale defaultLocale = Locale.getDefault();
    System.out.println(defaultLocale.getDisplayVariant());
    System.out.println(defaultLocale.getDisplayVariant(Locale.US));
    System.out.println((new Locale("en", "US", "WIN_TX_Austin")).getDisplayVariant());
  }
}
