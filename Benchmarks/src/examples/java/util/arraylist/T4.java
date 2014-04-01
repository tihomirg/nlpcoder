package examples.java.util.arraylist;
import java.util.ArrayList;
import java.util.Vector;

public class T4 {
  public static void main(String[] args) {
    ArrayList<String> arrayList = new ArrayList<String>();
    arrayList.add("1");
    arrayList.add("2");
    arrayList.add("3");

    Vector<String> v = new Vector<String>();
    v.add("4");
    v.add("5");

    // insert all elements of Vector to ArrayList at index 1
    arrayList.addAll(1, v);
    
    for (String str: arrayList)
      System.out.println(str);
  }
}