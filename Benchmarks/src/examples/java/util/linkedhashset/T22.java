package examples.java.util.linkedhashset;
import java.util.ArrayList;
import java.util.List;

public class T22 {
  public static void main(String[] argv) throws Exception {
    List<String> list1 = new ArrayList();
    List<String> list2 = new ArrayList();

    list1.addAll(list2);

    list1.removeAll(list2);

    list1.retainAll(list2);

    list1.clear();

    int newSize = 2;
    list1.subList(newSize, list1.size()).clear();
  }
}