package examples.java.util.arrays;
import java.util.Arrays;

public class T27 {
  public static void main(String[] argv) throws Exception {

    int startIndex = 0;
    int endIndex = 4;

    long[] longArr = new long[10];
    long longFillValue = 1;

    Arrays.fill(longArr, startIndex, endIndex, longFillValue);
  }
}
