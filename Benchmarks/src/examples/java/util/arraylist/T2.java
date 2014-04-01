package examples.java.util.arraylist;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class T2 {

  public static void main(String[] args) {
    Queue<String> queue = new LinkedList<String>();
    queue.add("Hello");
    queue.add("World");
    List<String> list = new ArrayList<String>(queue);

    System.out.println(list);
  }

}