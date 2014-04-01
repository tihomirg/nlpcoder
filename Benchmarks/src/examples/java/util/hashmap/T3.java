package examples.java.util.hashmap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class T3 {
  public static void main(String[] args) {
    HashMap<String, String> hMap = new HashMap<String, String>();

    hMap.put("1", "One");
    hMap.put("2", "Two");
    hMap.put("3", "Three");

    Set st = hMap.keySet();
    Iterator itr = st.iterator();

    while (itr.hasNext())
      System.out.println(itr.next());

    // remove 2 from Set
    st.remove("2");

    System.out.println(hMap.containsKey("2"));
  }
}