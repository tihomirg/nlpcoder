package examples.java.util.collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class T2 {
  public static void main(String args[]) {
    String simpsons[] = { "B", "H", "L", "M", "H", "M", "R" };

    List list = new ArrayList(Arrays.asList(simpsons));

    // Ensure list sorted
    Collections.sort(list);
    System.out.println("Sorted list: [length: " + list.size() + "]");
    System.out.println(list);

    // Search for element in list
    int index = Collections.binarySearch(list, "M");
    System.out.println("Found M @ " + index);

    // Search for element not in list
    index = Collections.binarySearch(list, "J");
    System.out.println("Didn't find J @ " + index);

  }
}