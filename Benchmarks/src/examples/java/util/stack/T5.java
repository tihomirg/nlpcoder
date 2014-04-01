package examples.java.util.stack;
import java.util.Stack;

public class T5 {
  public static void main (String args[]) {
    Stack<String> s = new Stack<String>();
    s.push("A");
    s.push("B");
    s.push("C");

    System.out.println("Next: " + s.peek());
    s.push("D");

    System.out.println(s.pop());
    s.push("E");
    s.push("F");

    int count = s.search("E");
    while (count != -1 && count > 1) {
      s.pop();
      count--;
    }
    System.out.println(s);
  }
}