package examples.java.util.stack;
import java.util.Stack;
import java.util.EmptyStackException;

public class T1 {

  public static void main(String args[]) {
    Stack st = new Stack();
    System.out.println("stack: " + st);
    st.push(new Integer(42));
    System.out.println("push(" + 42 + ")");
    System.out.println("stack: " + st);

    System.out.print("pop -> ");
    Integer a = (Integer) st.pop();
    System.out.println(a);
    System.out.println("stack: " + st);

    try {
      st.pop();
    } catch (EmptyStackException e) {
      System.out.println("empty stack");
    }
  }
}