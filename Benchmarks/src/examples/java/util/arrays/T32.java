package examples.java.util.arrays;
import java.util.Arrays;

public class T32 {
  public static void main(String[] argv) throws Exception {

    int startIndex = 0;
    int endIndex = 4;

    short[] shortArr = new short[10];
    short shortFillValue = 1;

    Arrays.fill(shortArr, startIndex, endIndex, shortFillValue);

  }
}