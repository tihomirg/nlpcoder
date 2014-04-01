package examples.java.util.arraylist;
import java.util.ArrayList;

public class T22 {
  public static void main(String[] args) {
    ArrayList<String> arrayList = new ArrayList<String>();

    arrayList.add("1");
    arrayList.add("2");
    arrayList.add("3");
    arrayList.add("4");
    arrayList.add("5");

    Object[] objArray = arrayList.toArray();

    for (Object obj : objArray)
      System.out.println(obj);
  }
}