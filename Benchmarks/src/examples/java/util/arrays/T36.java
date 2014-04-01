package examples.java.util.arrays;
import java.util.Arrays;

public class T36 {

  public static void main(String[] args) {
    char[] c1 = new char[] { 'c', 'h', 'a', 'r', 's' };
    for (char ch: c1){
      System.out.print(ch);
    }
    Arrays.sort(c1);

    for (char ch: c1){
      System.out.print(ch);
    }

    char[] c2 = new char[] { 'c', 'h', 'a', 'r', 's' };
    Arrays.sort(c2, 1, 4);
    for (char ch: c1){
      System.out.print(ch);
    }
  }
}