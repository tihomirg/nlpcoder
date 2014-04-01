package examples.java.util.linkedhashset;
import java.util.LinkedHashSet;

public class T2 {

  public static void main(String[] args) {
    LinkedHashSet<Integer> lhashSet = new LinkedHashSet<Integer>();

    System.out.println("Size of LinkedHashSet : " + lhashSet.size());
    lhashSet.add(new Integer("1"));
    lhashSet.add(new Integer("2"));
    lhashSet.add(new Integer("3"));

    System.out.println(lhashSet.size());

    lhashSet.remove(new Integer("1"));

    System.out.println(lhashSet.size());
  }
}