package examples.java.util.arrays;
import java.util.Arrays;

public class T13 {
  public static void main(String[] args) {
    String[] abc = { "a", "b", "c", "d" };
    String[] xyz = { "A", "b", "c", "s" };
    String[] java = { "Java", "Dot", "Com" };

    System.out.println(Arrays.equals(abc, xyz));
    System.out.println(Arrays.equals(abc, java));
  }
}