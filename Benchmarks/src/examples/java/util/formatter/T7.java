package examples.java.util.formatter;
import java.util.Formatter;

public class T7 {
  public static void main(String args[]) {
    Formatter fmt = new Formatter();
    fmt.format("|%f|%n|%12f|%n|%012f|", 10.12345, 10.12345, 10.12345);

    System.out.println(fmt);

  }
}