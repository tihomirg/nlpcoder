package examples.java.util.collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class T14 {
  public static void main(String[] args) {
    List numbers = new ArrayList();

    for (int i = 0; i < 25; i++) {
      numbers.add(i);
    }

    System.out.println(Arrays.toString(numbers.toArray()));

    Collections.rotate(numbers, 10);

    System.out.println(Arrays.toString(numbers.toArray()));
  }
}