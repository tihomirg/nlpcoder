package examples.java.util.linkedhashset;
import java.util.LinkedHashSet;

public class T6 {
  public static void main(String[] args) {
    LinkedHashSet<Integer> lhashSet = new LinkedHashSet<Integer>();

    lhashSet.add(new Integer("1"));
    lhashSet.add(new Integer("2"));
    lhashSet.add(new Integer("3"));

    Object[] objArray = lhashSet.toArray();

    for (Object inte: objArray){
      System.out.println(inte);
    }
  }
}