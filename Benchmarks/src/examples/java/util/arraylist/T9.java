package examples.java.util.arraylist;
import java.util.ArrayList;
import java.util.List;

public class T9 {
  public static void main(String args[]) throws Exception {

    List<String> list = new ArrayList<String>();
    list.add("A");
    list.add("B");
    list.add("C");

    System.out.println(list.get(2));

  }
}