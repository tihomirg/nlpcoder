package examples.java.util.random;
import java.util.Random;

public class T5 {
  public static void main(String[] args) {
    Random rand = new Random();
    int randomInt = rand.nextInt(10);
    long randomLong = rand.nextLong() * 10;
    float randomFloat = rand.nextLong() * 10;
    double randomDouble = rand.nextDouble() * 10;
  }
}