package examples.java.util.collections;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class T22 {
  public static void main(String args[]) throws Exception {

    List<String> list = new ArrayList<String>();
    list.add("A");
    list.add("B");
    list.add("C");
    list = Collections.unmodifiableList(list);
    list.add(1, "G");

    System.out.println(list);

  }
}