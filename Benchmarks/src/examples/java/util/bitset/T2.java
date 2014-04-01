package examples.java.util.bitset;
import java.util.BitSet;

public class T2 {
	  public static void main(String args[]) {
	    BitSet bites = new BitSet();
	    bites.set(0);
	    bites.set(1);
	    bites.set(2);
	    bites.set(3);

	    System.out.println(bites.size());
	    System.out.println(bites.length());
	  }
}