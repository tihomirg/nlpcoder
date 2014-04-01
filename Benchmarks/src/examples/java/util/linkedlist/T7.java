package examples.java.util.linkedlist;
import java.util.LinkedList;
import java.util.List;

public class T7 {

  public static void main(String[] args) {

    List<String> theList = new LinkedList<String>();
    theList.add("A");
    theList.add("B");
    theList.add("C");
    theList.add("D");

    String[] my = theList.toArray(new String[0]);

    for (int i = 0; i < my.length; i++) {
      System.out.println(my[i]);
    }
  }
}