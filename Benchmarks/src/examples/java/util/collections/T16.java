package examples.java.util.collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class T16 {
  public static void main(String args[]) {
    String init[] = { "One", "Two", "Three", "One", "Two", "Three" };
    List list1 = new ArrayList(Arrays.asList(init));
    List list2 = new ArrayList(Arrays.asList(init));
    list1.remove("One");
    System.out.println(list1);
    list2.removeAll(Collections.singleton("One"));
    System.out.println(list2);
  }
}