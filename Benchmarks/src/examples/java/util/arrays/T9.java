package examples.java.util.arrays;
import java.util.Arrays;
public class T9 {
  public static void main(String[] a) {
    String str[] = { "B", "H", "L", "M", "I", "N", "R" };
    // Ensure list sorted
    Arrays.sort(str);
    for (String s : str) {
      System.out.println(s);
    }
    // Search for element in list
    int index = Arrays.binarySearch(str, "M");
    System.out.println("Found M @ " + index);
  }
}