package examples.java.util.linkedhashset;
import java.util.Arrays;
import java.util.List;
public class T21 {
  public static void main(String[] a) {
    List<String> list = Arrays.asList(new String[] { "A", "B", "C", "D" });
    list.set(2, "X");
    System.out.println(list);
  }
}