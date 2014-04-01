package examples.java.util.bitset;
import java.util.BitSet;
public class T3 {
 public static void main (String args[]) {
    BitSet bites = new BitSet();
    bites.set(0);
    bites.set(1);
    bites.set(2);
    bites.set(3);
    System.out.println(bites);
 
    bites.andNot(bites);
    System.out.println(bites);

 }
}