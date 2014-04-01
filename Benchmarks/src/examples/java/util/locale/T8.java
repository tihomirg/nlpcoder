package examples.java.util.locale;
import java.util.Locale;

public class T8 {
  public static void main(String args[]) throws Exception {
    Locale defaultLocale = Locale.getDefault();
    System.out.println(defaultLocale.getDisplayName());
    System.out.println(defaultLocale.getDisplayName(Locale.ITALIAN));
    System.out.println(defaultLocale.getDisplayName(Locale.US));
  }
}