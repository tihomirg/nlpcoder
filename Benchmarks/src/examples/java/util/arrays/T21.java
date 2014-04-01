package examples.java.util.arrays;
import java.util.Arrays;

public class T21 {

  public static void main(String[] arg) {
    double[] data = new double[50]; // An array of 50 values of type double
    Arrays.fill(data, 1.0);                     // Fill all elements of data with 1.0
    
    for (double d: data) { 
      System.out.println(d);
    }
    
  }
}