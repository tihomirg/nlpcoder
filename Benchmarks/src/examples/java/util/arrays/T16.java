package examples.java.util.arrays;
import java.util.Arrays;

public class T16 {
  public static void main(String[] argv) throws Exception {
    int startIndex = 0;
    int endIndex = 4;

    boolean[] booleanArr = new boolean[10];
    boolean booleanFillValue = true;

    Arrays.fill(booleanArr, startIndex, endIndex, booleanFillValue);

  }
}