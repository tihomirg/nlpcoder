package examples.java.util.priorityqueue;
import java.util.Comparator;
import java.util.PriorityQueue;

enum ProductQuality {
  High, Medium, Low
}

class Product implements Comparable<Product> {
  String name;

  ProductQuality priority;

  Product(String str, ProductQuality pri) {
    name = str;
    priority = pri;
  }

  public int compareTo(Product msg2) {
    return priority.compareTo(msg2.priority);
  }
}

class MessageComparator implements Comparator<Product> {
  public int compare(Product msg1, Product msg2) {
    return msg2.priority.compareTo(msg1.priority);
  }
}

public class T1 {
  public static void main(String args[]) {

    PriorityQueue<Product> pq = new PriorityQueue<Product>(3);

    pq.add(new Product("A", ProductQuality.Low));
    pq.add(new Product("B", ProductQuality.High));
    pq.add(new Product("C", ProductQuality.Medium));
    Product m;
    while ((m = pq.poll()) != null)
      System.out.println(m.name + " Priority: " + m.priority);

    PriorityQueue<Product> pqRev = new PriorityQueue<Product>(3, new MessageComparator());

    pqRev.add(new Product("D", ProductQuality.Low));
    pqRev.add(new Product("E", ProductQuality.High));
    pqRev.add(new Product("F", ProductQuality.Medium));

    while ((m = pqRev.poll()) != null)
      System.out.println(m.name + " Priority: " + m.priority);
  }
}