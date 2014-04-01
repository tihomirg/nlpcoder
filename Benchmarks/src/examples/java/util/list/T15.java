package examples.java.util.list;
import java.util.Arrays;
import java.util.List;
public class T15 {
  public static void main(String[] a) {
    List<String> list = Arrays.asList(new String[] { "A", "B", "C", "D" });
    list.set(2, "X");
    System.out.println(list);
  }
}