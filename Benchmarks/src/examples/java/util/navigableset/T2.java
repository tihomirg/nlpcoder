package examples.java.util.navigableset;
import java.util.NavigableSet;
import java.util.TreeSet;

public class T2 {
  public static void main(String[] args) {
    String[] cities = { "A", "B", "C", "D", "E", "F" };
    NavigableSet<String> citiesSet = new TreeSet<String>();
    for (String city : cities)
      citiesSet.add(city);

    for (String city : citiesSet.descendingSet())
      System.out.println("  " + city);
  }
}