package examples.java.util.arrays;
import java.util.Arrays;
public class T17 {
  public static void main(String[] a) {
    byte array[] = new byte[10];
//    Arrays.fill(array, 4); // illegal
    for (int i : array) {
      System.out.println(i);
    }
    Arrays.fill(array, (byte) 4); // Okay
    for (int i : array) {
      System.out.println(i);
    }
  }
}