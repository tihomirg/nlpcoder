package examples.java.util.stack;
import java.util.Stack;

public class T3 {
  public static void main (String args[]) {
    Stack<String> s = new Stack<String>();
    s.push("A");
    s.push("B");
    s.push("C");

    System.out.println(s.pop());
    System.out.println(s.empty());
  }
}  
