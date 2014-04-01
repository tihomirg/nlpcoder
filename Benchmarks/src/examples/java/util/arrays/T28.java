package examples.java.util.arrays;
import java.util.Arrays;
import java.util.Date;
public class T28 {
  public static void main(String args[]) {
    int array[] = new int[10];
    Arrays.fill(array, 100);
    for (int i=0, n=array.length; i<n; i++) {
      System.out.println(array[i]);
    }
    System.out.println();
    Arrays.fill(array, 3, 6, 50);
    for (int i=0, n=array.length; i<n; i++) {
      System.out.println(array[i]);
    }
    byte array2[] = new byte[10];
    Arrays.fill(array2, (byte)4);

    System.out.println();
    Date array3[] = new Date[10];
    Arrays.fill(array3, "Help");
  }
}