package examples.java.util.arrays;
import java.util.*;
public class T7 {
  public static void main (String args[]) {

    String[] a = new String[]{"a","b","c"};
    List list = Arrays.asList(a);

    Iterator i = list.iterator();
    while (i.hasNext()) {
      System.out.println(i.next());
    }
  }
}