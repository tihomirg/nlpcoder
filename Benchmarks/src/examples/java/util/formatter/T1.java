package examples.java.util.formatter;
import java.util.Formatter;

public class T1 {

  public static void main(String[] args) throws Exception {
    StringBuilder buf = new StringBuilder(); // Buffer to hold output
    Formatter formatter = new Formatter(buf); // Formatter to format data into
                                              // buf
    formatter.format("%4d)%+7.2f", 2, 1234.1234);
    System.out.println(buf);

  }

}