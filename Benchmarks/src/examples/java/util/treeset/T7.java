package examples.java.util.treeset;
import java.util.TreeSet;

public class T7 {
  public static void main(String[] args) {
    TreeSet<Integer> tSet = new TreeSet<Integer>();
    System.out.println("Size of TreeSet : " + tSet.size());

    tSet.add(new Integer("1"));
    tSet.add(new Integer("2"));
    tSet.add(new Integer("3"));

    System.out.println(tSet.size());

    // remove one element from TreeSet using remove method

    tSet.remove(new Integer("1"));
    System.out.println("Size of TreeSet after removal : " + tSet.size());
  }
}