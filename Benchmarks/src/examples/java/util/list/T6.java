package examples.java.util.list;
import java.util.Arrays;
import java.util.List;

public class T6 {

  public static void main(String[] a) {

    List list = Arrays.asList(new String[] { "A", "B", "C", "D" });
    List list2 = Arrays.asList(new String[] { "A", "B", "C" });

    System.out.println(list.equals(list2));
  }
}