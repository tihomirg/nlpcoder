package examples.java.util.linkedlist;
import java.util.Iterator;
import java.util.LinkedList;

public class T5 {
  public static void main(String[] args) {
    LinkedList<String> lList = new LinkedList<String>();
    lList.add("1");
    lList.add("2");
    lList.add("3");
    lList.add("4");
    lList.add("5");

    Iterator itr = lList.iterator();
    while (itr.hasNext()) {
      System.out.println(itr.next());
    }
  }
}