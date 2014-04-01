package examples.java.util.set;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class T14 {

  public static void main(String[] a) {
    String elements[] = { "A", "B", "C", "D", "E" };
    Set<String> set = new HashSet<String>(Arrays.asList(elements));

    Object[] arrObj = set.toArray();

    for (int i = 0; i < arrObj.length; i++) {
      System.out.println(arrObj[i]);
    }

    System.out.println(set); 
  }
} 