package examples.java.util.hashtable;
import java.util.Hashtable;

public class T8 {

  public static void main(String args[]) {

    Hashtable ht = new Hashtable();
    ht.put("Tokyo", "Japan");
    ht.put("Beijing", "China");
    ht.put("Bangkok", "Thailand");

    String city = "Beijing";
    String country = (String) ht.get(city);
    if (country != null)
      System.out.println(city + " is located in " + country);
    else
      System.out.println(city + " is not located in the hashtable");
  }
}