package examples.java.util.arrays;
import java.util.Arrays;
public class T11 {
  public static void main(String[] a) {
    int[] i = new int[] { 1, 2, 3 };
    int[] j = new int[] { 1, 2, 4 };
    System.out.println(Arrays.equals(i, j));
  }
}