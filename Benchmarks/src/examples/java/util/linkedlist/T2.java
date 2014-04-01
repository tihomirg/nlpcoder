package examples.java.util.linkedlist;
import java.util.LinkedList;

public class T2 {
	  public static void main(String[] args) {
	    LinkedList<String> lList = new LinkedList<String>();

	    lList.add("1");
	    lList.add("2");
	    lList.add("3");
	    lList.add("4");
	    lList.add("5");

	    System.out.println("LinkedList contains : " + lList);
	    lList.clear();
	    System.out.println("LinkedList now contains : " + lList);
	  }
}