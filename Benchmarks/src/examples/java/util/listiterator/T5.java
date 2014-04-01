package examples.java.util.listiterator;
import java.util.ArrayList;
import java.util.ListIterator;

public class T5 {
  public static void main(String[] args) {
    ArrayList<String> aList = new ArrayList<String>();

    aList.add("1");
    aList.add("2");
    aList.add("3");
    aList.add("4");
    aList.add("5");

    ListIterator<String> listIterator = aList.listIterator();
    listIterator.next();

    listIterator.set("100");
    for (String str : aList) {
      System.out.println(str);
    }
  }
}