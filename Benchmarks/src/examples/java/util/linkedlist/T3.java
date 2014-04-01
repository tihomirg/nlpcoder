package examples.java.util.linkedlist;
import java.util.LinkedList;

import java.util.LinkedList;

public class T3 {
  public static void main(String[] args) {
    LinkedList<String> lList = new LinkedList<String>();
    lList.add("1");
    lList.add("2");
    lList.add("3");
    lList.add("4");
    lList.add("5");

    System.out.println("First element of LinkedList is : " + lList.getFirst());
    System.out.println("Last element of LinkedList is : " + lList.getLast());
  }
}