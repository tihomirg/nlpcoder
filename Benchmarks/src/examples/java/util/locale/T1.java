package examples.java.util.locale;
import java.util.Locale;

public class T1 {
  public static void main(String args[]) {
    Locale currentLocale = Locale.getDefault();
    Locale locales[] = { Locale.TAIWAN, Locale.KOREA, Locale.ITALY, Locale.CANADA,
        Locale.CANADA_FRENCH };
    System.out.println("CURRENT LOCALE:");
    describeLocale(currentLocale);
    System.out.println("OTHER LOCALES:");
    for (int i = 0; i < locales.length; ++i)
      describeLocale(locales[i]);
  }

  static void describeLocale(Locale l) {
    System.out.println("Country: " + l.getDisplayCountry());
    System.out.println("Language: " + l.getDisplayLanguage());
    System.out.println();
  }
}