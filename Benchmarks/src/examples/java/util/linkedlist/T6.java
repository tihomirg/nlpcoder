package examples.java.util.linkedlist;
import java.util.LinkedList;
import java.util.List;

public class T6 {
  public static void main(String[] args) {
    LinkedList<String> lList = new LinkedList<String>();

    lList.add("1");
    lList.add("2");
    lList.add("3");
    lList.add("4");
    lList.add("5");

    System.out.println(lList);
    List lst = lList.subList(1, 4);
    System.out.println(lst);

    lst.remove(2);
    System.out.println(lst);
    System.out.println(lList);
  }
}