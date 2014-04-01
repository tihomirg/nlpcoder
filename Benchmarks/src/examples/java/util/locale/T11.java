package examples.java.util.locale;
import java.util.Locale;

public class T11 {
  public static void main(String args[]) throws Exception {
    String[] languages = Locale.getISOLanguages();

    System.out.println("\nLanguages:\n");
    for (int i = 0; i < languages.length; i++)
      System.out.println(languages[i]);
  }
}