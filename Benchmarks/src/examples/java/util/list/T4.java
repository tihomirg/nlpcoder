package examples.java.util.list;
import java.util.ArrayList;
import java.util.List;

public class T4 {
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