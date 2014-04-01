package examples.java.util.locale;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class T6 {

  public static void main(String[] args) {
    Date today = new Date();
    Locale[] locales = { Locale.US, Locale.UK, Locale.GERMANY, Locale.FRANCE };
    
    int[] styles = { DateFormat.FULL, DateFormat.LONG, DateFormat.MEDIUM,
        DateFormat.SHORT };
        
    DateFormat fmt;
    String[] styleText = { "FULL", "LONG", "MEDIUM", "SHORT" };

    // Output the date for each local in four styles
    for (int i = 0; i < locales.length; i++) {
      System.out.println("\nThe Date for " + locales[i].getDisplayCountry()
          + ":");
      for (int j = 0; j < styles.length; j++) {
        fmt = DateFormat.getDateInstance(styles[j], locales[i]);
        System.out.println("\tIn " + styleText[j] + " is " + fmt.format(today));
      }
    }
  }
}