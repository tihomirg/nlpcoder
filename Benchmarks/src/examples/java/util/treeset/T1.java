package examples.java.util.treeset;
import java.util.TreeSet;

public class T1 {
  public static void main(String args[]) {
    TreeSet<String> ts = new TreeSet<String>();

    ts.add("C");
    ts.add("A");
    ts.add("B");
    ts.add("E");
    ts.add("F");
    ts.add("D");

    System.out.println(ts);
  }
}