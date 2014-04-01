package examples.java.util.arraylist;
import java.util.ArrayList;
import java.util.List;

public class T17 {
  public static void main(String args[]) throws Exception {

    List<String> list = new ArrayList<String>();
    list.add("A");
    list.add("A");
    list.add("B");
    list.add("B");
    list.add("C");
    list.add("C");

    List<String> list2 = new ArrayList<String>();
    list2.add("A");
    list2.add("B");
    list2.add("C");
    list.removeAll(list2);

    System.out.println(list);
  }
}