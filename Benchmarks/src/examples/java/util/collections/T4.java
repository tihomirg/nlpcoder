package examples.java.util.collections;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class T4 {
  public static void main(String args[]) throws Exception {
    List wineMakers = Arrays.asList(new String[] { "U", "C" });
    List barFlies = Arrays.asList(new String[] { "U", "C", "L" });
    Collections.copy(barFlies, wineMakers);
    System.out.println(barFlies);
    Collections.copy(wineMakers, barFlies);
  }
}