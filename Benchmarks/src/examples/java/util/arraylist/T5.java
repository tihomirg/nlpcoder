package examples.java.util.arraylist;
import java.util.ArrayList;
import java.util.List;

public class T5 {
  public static void main(String args[]) throws Exception {

    List<String> list = new ArrayList<String>();
    list.add("A");
    list.add("B");
    list.add("C");
    list.clear();

    System.out.println(list);
  }
}