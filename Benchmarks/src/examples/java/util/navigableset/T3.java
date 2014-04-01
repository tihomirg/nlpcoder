package examples.java.util.navigableset;
import java.util.NavigableSet;
import java.util.TreeSet;

public class T3 {
  

  public static void main(String[] args) {
    String[] cities = { "A", "B", "C", "D", "E", "F" };

    NavigableSet<String> citiesSet = new TreeSet<String>();
    for (String city : cities)
      citiesSet.add(city);


    System.out.println(citiesSet.floor("A"));
  }


}