package examples.java.util.list;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class T1 {

  public static void main(String[] args) {
    List<String> myList = new ArrayList<String>();
    myList.add("Hello");
    myList.add("World");
    myList.add("World");
    Set<String> set = new HashSet<String>(myList);

    System.out.println(set);
  }

}