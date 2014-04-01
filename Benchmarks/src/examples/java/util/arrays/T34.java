package examples.java.util.arrays;
import java.util.Arrays;

public class T34 {

  public static void main(String[] args) {
    byte[] b1 = new byte[] { 3, 2, 5, 4, 1 };
    for (byte b: b1){
      System.out.println(b);
    }

    Arrays.sort(b1);
    for (byte b: b1){
      System.out.println(b);
    }

    byte[] b2 = new byte[] { 5, 2, 3, 1, 4 };
    Arrays.sort(b2, 1, 4);

    for (byte b: b2){
      System.out.println(b);
    }
  }

}