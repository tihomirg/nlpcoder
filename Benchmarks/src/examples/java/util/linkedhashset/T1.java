package examples.java.util.linkedhashset;
import java.util.LinkedHashSet;
import java.util.Set;

public class T1 {

  public static Set fill(Set a, int size) {
    for (int i = 0; i < size; i++)
      a.add(new Integer(i));
    return a;
  }

  public static void test(Set a) {
    fill(a, 10);
    System.out.println(a);
  }

  public static void main(String[] args) {
    test(new LinkedHashSet());
  }
}