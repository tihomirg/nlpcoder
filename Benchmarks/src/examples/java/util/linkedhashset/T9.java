package examples.java.util.linkedhashset;
import java.util.ArrayList;
import java.util.List;

public class T9 {
  public static void main(String args[]) throws Exception {

    List<String> list = new ArrayList<String>();
    list.add("A");
    list.add("B");
    list.add("C");
    list.clear();

    System.out.println(list);
  }
}