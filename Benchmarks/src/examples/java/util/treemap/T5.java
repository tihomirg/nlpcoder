package examples.java.util.treemap;
import java.util.TreeMap;

public class T5 {
  public static void main(String[] args) {
    TreeMap<String,String> treeMap = new TreeMap<String,String>();
    treeMap.put("1", "One");
    treeMap.put("2", "Two");
    treeMap.put("3", "Three");

    treeMap.clear();
    System.out.println(treeMap.size());
  }
}