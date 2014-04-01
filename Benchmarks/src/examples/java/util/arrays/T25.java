package examples.java.util.arrays;
import java.util.Arrays;
public class T25 {
  public static void main(String[] a) {
    int array[] = new int[10];
    Arrays.fill(array, 100);
    for(int i: array){
      System.out.println(i);
    }
    Arrays.fill(array, 3, 6, 50);
    for(int i: array){
      System.out.println(i);
    }
  }
}