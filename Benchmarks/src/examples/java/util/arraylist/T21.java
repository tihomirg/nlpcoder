package examples.java.util.arraylist;
import java.util.ArrayList;

public class T21 {
  public static void main(String[] args) {
    ArrayList myList = new ArrayList(5);
    for (int i = 0; i < 5; i++) {
      myList.add(new Integer(i));
    }
    System.out.println("List contains " + myList.size() + " elements");

    Integer int2 = new Integer(2);
    System.out.println("List contains Integer(2): " + myList.contains(int2));
    System.out.println("Integer(2) is at index " + myList.indexOf(int2));

    myList.set(2, new Integer(99));
    System.out.println("Get element at index 2: " + myList.get(2));

    myList.ensureCapacity(15);
    for (int i = 10; i < 15; i++) {
      myList.add(new Integer(i));
    }

    myList.subList(10, 15).clear();
    myList.trimToSize();


    System.out.println(myList);
  }
}