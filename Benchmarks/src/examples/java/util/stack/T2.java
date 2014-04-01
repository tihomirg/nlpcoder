package examples.java.util.stack;
import java.util.EmptyStackException;
import java.util.Stack;
  
public class T2 {
  static void showpush(Stack<Integer> st, int a) { 
    st.push(a); 
    System.out.println("push(" + a + ")"); 
    System.out.println("stack: " + st); 
  } 
 
  static void showpop(Stack<Integer> st) { 
    System.out.print("pop -> "); 
    Integer a = st.pop(); 
    System.out.println(a); 
    System.out.println("stack: " + st); 
  } 
 
  public static void main(String args[]) { 
    Stack<Integer> st = new Stack<Integer>(); 
 
    System.out.println("stack: " + st); 
    showpush(st, 2); 
    showpush(st, 6); 
    showpush(st, 9); 
    showpop(st); 
 
    try { 
      showpop(st); 
    } catch (EmptyStackException e) { 
      System.out.println("empty stack"); 
    } 
  } 
}