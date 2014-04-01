package examples.java.util.list;
import java.util.ArrayList;
import java.util.List;

public class T12 {
  public static void main(String args[]) throws Exception {

    List<String> list = new ArrayList<String>();
    list.add("A");
    list.add("B");
    list.add("C");

    System.out.println(list.remove(0));
    System.out.println(list.remove("B"));
    System.out.println(list);

  }
}