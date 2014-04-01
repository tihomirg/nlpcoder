package examples.java.util.arrays;
import java.util.Arrays;

public class T22 {
  public static void main(String[] argv) throws Exception {

    int startIndex = 0;
    int endIndex = 4;
    double[] doubleArr = new double[10];
    double doubleFillValue = 1;
    Arrays.fill(doubleArr, startIndex, endIndex, doubleFillValue);
  }
}