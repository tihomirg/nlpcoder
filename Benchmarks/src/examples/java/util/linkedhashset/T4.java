package examples.java.util.linkedhashset;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class T4 {
  public static void main(String[] args) {
    LinkedHashSet<Integer> lhashSet = new LinkedHashSet<Integer>();

    lhashSet.add(new Integer("1"));
    lhashSet.add(new Integer("2"));
    lhashSet.add(new Integer("3"));

    Iterator itr = lhashSet.iterator();

    while (itr.hasNext()){
      System.out.println(itr.next());
    }
  }
}