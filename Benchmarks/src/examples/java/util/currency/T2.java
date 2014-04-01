package examples.java.util.currency;
/*
 * Output: 
Symbol: $
Default fractional digits: 2
 */

import java.util.Currency;
import java.util.Locale;

public class T2 {
  public static void main(String args[]) {
    Currency c;

    c = Currency.getInstance(Locale.US);

    System.out.println("Symbol: " + c.getSymbol());
    System.out.println("Default fractional digits: "
        + c.getDefaultFractionDigits());
  }
}