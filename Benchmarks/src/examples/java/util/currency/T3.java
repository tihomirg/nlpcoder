package examples.java.util.currency;
import java.util.Currency;
import java.util.Locale;

public class T3 {
  public static void main(String args[]) {
    Currency c;

    c = Currency.getInstance(Locale.US);

    System.out.println("Symbol: " + c.getSymbol());
    System.out.println("Default fractional digits: "
        + c.getDefaultFractionDigits());
  }
}