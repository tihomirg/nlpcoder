package examples.java.util.treemap;
import java.util.TreeMap;

public class T4 {

  public static void main(String[] args) {
    TreeMap<Integer, Product> db = new TreeMap<Integer, Product>();
    db.put(1000, new Product("D", 350));
    db.put(1011, new Product("p", 15.75));
    db.put(1102, new Product("M", 8.50));
    db.put(2023, new Product("A", 150));
    db.put(2034, new Product("T", 9.99));

    System.out.println(db.subMap(1000, 1999) + "\n");

    System.out.println(db.tailMap(1011) + "\n");

    System.out.println(db.headMap(2023));

    System.out.println("First key higher than 2034: " + db.higherKey(2034));
    System.out.println("First key lower than 2034: " + db.lowerKey(2034));
  }
}

class Product {
  String desc;

  double price;

  Product(String desc, double price) {
    this.desc = desc;
    this.price = price;
  }

  public String toString() {
    return "Description=" + desc + ", Price=" + price;
  }
}
