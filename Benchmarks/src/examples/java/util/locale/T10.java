package examples.java.util.locale;
import java.util.Locale;

public class T10 {
  public static void main(String args[]) throws Exception {
    String [] countries = Locale.getISOCountries();

    System.out.println("Countries:\n");
    for (int i = 0; i < countries.length; i++)
      System.out.println(countries[i]);
  }
}