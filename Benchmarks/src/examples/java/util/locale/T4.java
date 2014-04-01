package examples.java.util.locale;
import java.text.NumberFormat;
import java.util.Locale;

public class T4 {
  public static void main(String args[]) throws Exception {
    NumberFormat numberFormat = NumberFormat.getInstance();
    numberFormat.setParseIntegerOnly(false);
    double usersNumber = 1976.0826;

    numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY);
    System.out.println("User's number (GERMANY): " + numberFormat.format(usersNumber));
    
  }
}