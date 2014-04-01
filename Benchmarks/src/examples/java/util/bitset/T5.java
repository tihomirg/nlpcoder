package examples.java.util.bitset;
import java.util.BitSet;

public class T5 {
  public static void main(String[] argv) {
    BitSet bs = new BitSet();
    bs.set(65);
    System.out.println("Bit 65 is " + bs.get(65));
  }
}
