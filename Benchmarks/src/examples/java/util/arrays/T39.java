package examples.java.util.arrays;
import java.util.Arrays;

public class T39 {

  public static void main(String[] args) {
    float[] f1 = new float[] { 3.1f, 2.1f, 5.1f, 4.1f, 1.1f };

    for (float f: f1){
      System.out.print(" " + f);
    }
    Arrays.sort(f1);

    for (float f: f1){
      System.out.print(" " + f);
    }

    float[] f2 = new float[] { 5.1f, 2.1f, 3.1f, 1.1f, 4.1f };
    Arrays.sort(f2, 1, 4);

    for (float f: f2){
      System.out.print(" " + f);
    }
  }
}