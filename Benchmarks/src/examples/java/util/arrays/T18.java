package examples.java.util.arrays;
import java.util.Arrays;

public class T18 {
  public static void main(String[] argv) throws Exception {

    int startIndex = 0;
    int endIndex = 4;

    byte[] byteArr = new byte[10];
    byte byteFillValue = 1;

    Arrays.fill(byteArr, startIndex, endIndex, byteFillValue);
  }
}
