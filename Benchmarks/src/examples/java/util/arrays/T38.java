package examples.java.util.arrays;
import java.util.Arrays;

public class T38 {
  public static void main(String[] args) {
    double[] d1 = new double[] { 3.1, 2.1, 5.1, 4.1, 1.1 };
    for (double d: d1){
      System.out.print(" " +d);
    }
    Arrays.sort(d1);

    for (double d: d1){
      System.out.print(" " +d);
    }
 
    double[] d2 = new double[] { 5, 2, 3, 1, 4 };
    Arrays.sort(d2, 1, 4);
    for (double d: d2){
      System.out.print(" " +d);
    } 
  }
}