package examples.java.util.list;
import java.util.Arrays;
import java.util.List;
public class T8 {
  public static void main(String[] a) {
    List list = Arrays.asList(new String[] { "A", "B", "C", "D" });
    System.out.println(list.size());
    System.out.println(list.isEmpty());
  }
}