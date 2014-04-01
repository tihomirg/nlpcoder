package examples.java.util.arrays;
import java.util.Arrays;

public class T24 {
  public static void main(String[] argv) throws Exception {

    int startIndex = 0;
    int endIndex = 4;

    float[] floatArr = new float[10];
    float floatFillValue = 1;

    Arrays.fill(floatArr, startIndex, endIndex, floatFillValue);
  }
}