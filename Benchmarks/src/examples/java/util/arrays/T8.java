package examples.java.util.arrays;
/**
 *Output: 
Original contents: 0 3 6 9 12 15 18 21 24 27 
Sorted: 0 3 6 9 12 15 18 21 24 27 
After fill(): 0 3 -1 -1 -1 -1 18 21 24 27 
After sorting again: -1 -1 -1 -1 0 3 18 21 24 27 
The value 9 is at location -7
 */

import java.util.Arrays;

public class T8 {
  public static void main(String args[]) {

    int array[] = new int[10];
    for (int i = 0; i < 10; i++)
      array[i] = 3 * i;

    System.out.print("Original contents: ");
    display(array);
    Arrays.sort(array);
    System.out.print("Sorted: ");
    display(array);

    Arrays.fill(array, 2, 6, -1);
    System.out.print("After fill(): ");
    display(array);

    Arrays.sort(array);
    System.out.print("After sorting again: ");
    display(array);

    System.out.print("The value 9 is at location ");
    int index = Arrays.binarySearch(array, 9);

    System.out.println(index);
  }

  static void display(int array[]) {
    for (int i : array) {
      System.out.print(i + " ");
    }
    System.out.println();
  }
}