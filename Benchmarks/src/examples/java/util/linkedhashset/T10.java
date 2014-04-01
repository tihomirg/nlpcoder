package examples.java.util.linkedhashset;
import java.util.ArrayList;
import java.util.List;

public class T10 {
  public static void main(String[] a) {

    List<String> list = new ArrayList<String>();
    list.add("A");

    List list2 = ((List) ((ArrayList) list).clone());

    System.out.println(list);
    System.out.println(list2);

    list.clear();

    System.out.println(list);
    System.out.println(list2);
  }
}