package examples.java.util.treeset;
import java.util.Comparator;
import java.util.TreeSet;

class MyComparator implements Comparator<String> {
  public int compare(String a, String b) {
    String aStr, bStr;

    aStr = a;
    bStr = b;

    return bStr.compareTo(aStr);
  }
  // No need to override equals.
}

public class T3 {
  public static void main(String args[]) {
    TreeSet<String> ts = new TreeSet<String>(new MyComparator());

    ts.add("C");
    ts.add("A");
    ts.add("B");
    ts.add("E");
    ts.add("F");
    ts.add("D");

    for (String element : ts)
      System.out.print(element + " ");

    System.out.println();
  }
}