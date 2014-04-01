package examples.java.util.arraydeque;
import java.util.ArrayDeque;
import java.util.Deque;

public class T1 {
  public static void main(String[] args) {
    MyDequeue stack = new MyDequeue();
    for (int i = 0; i < 5; i++) {
      stack.push(i);
    }
    System.out.println("After pushing 5 elements: " + stack);

    int m = stack.pop();
    System.out.println("Popped element = " + m);

    System.out.println("After popping 1 element : " + stack);

    int n = stack.peek();
    System.out.println("Peeked element = " + n);
    System.out.println("After peeking 1 element : " + stack);
  }
}

class MyDequeue {
  private Deque<Integer> data = new ArrayDeque<Integer>();

  public void push(Integer element) {
    data.addFirst(element);
  }

  public Integer pop() {
    return data.removeFirst();
  }

  public Integer peek() {
    return data.peekFirst();
  }

  public String toString() {
    return data.toString();
  }

}