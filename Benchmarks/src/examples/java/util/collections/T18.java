package examples.java.util.collections;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class T18 {
  public static void main(String[] args) {
    Collection<String> c = Collections.synchronizedCollection(new ArrayList<String>());
    List<String> list = Collections.synchronizedList(new ArrayList<String>());
    Set<String> s = Collections.synchronizedSet(new HashSet<String>());
    Map<String,String> m = Collections.synchronizedMap(new HashMap<String,String>());
  }
}