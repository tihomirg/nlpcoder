package examples.java.util.linkedhashset;
import java.util.LinkedHashSet;

public class T3 {
  public static void main(String[] args) {
    LinkedHashSet<Integer> lhashSet = new LinkedHashSet<Integer>();

    lhashSet.add(new Integer("1"));
    lhashSet.add(new Integer("2"));
    lhashSet.add(new Integer("3"));

    System.out.println(lhashSet);

    lhashSet.clear();

    System.out.println(lhashSet);

    System.out.println(lhashSet.isEmpty());
  }
}