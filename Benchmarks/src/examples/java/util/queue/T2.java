package examples.java.util.queue;
import java.util.LinkedList;
import java.util.Queue;

public class T2 {
  public static void main(String[] args) {
    Queue<String> queue = new LinkedList<String>();
    queue.offer("First");
    queue.offer("Second");
    queue.offer("Third");
    queue.offer("Fourth");

    System.out.println("Size: " + queue.size());

    System.out.println("Queue head using peek   : " + queue.peek());
    System.out.println("Queue head using element: " + queue.element());

    Object data;
    while ((data = queue.poll()) != null) {
      System.out.println(data);
    }
  }
}