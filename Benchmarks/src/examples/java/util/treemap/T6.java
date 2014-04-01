package examples.java.util.treemap;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

public class T6 {
  public static void main(String[] args) {
    TreeMap<String, String> treeMap = new TreeMap<String,String>();
    treeMap.put("1", "One");
    treeMap.put("2", "Two");
    treeMap.put("3", "Three");

    Collection c = treeMap.values();
    Iterator itr = c.iterator();

    while (itr.hasNext()){
      System.out.println(itr.next());
    }
  }
}