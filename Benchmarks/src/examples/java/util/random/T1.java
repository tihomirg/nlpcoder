package examples.java.util.random;
import java.util.Random;

public class T1 {

  public static void main(String args[]) {
    Random generator = new Random(100);

    System.out.println("First generator:");
    for(int i = 0; i < 10; i++)
      System.out.println(generator.nextInt());

  }
}