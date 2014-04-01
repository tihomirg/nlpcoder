package examples.java.util.arrays;
import java.util.Arrays;

public class T20 {
  public static void main(String[] argv) throws Exception {

    int startIndex = 0;
    int endIndex = 4;

    char[] charArr = new char[10];
    char charFillValue = 1;

    Arrays.fill(charArr, startIndex, endIndex, charFillValue);
  }
}