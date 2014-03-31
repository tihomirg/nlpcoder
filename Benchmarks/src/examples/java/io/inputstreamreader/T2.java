package examples.java.io.inputstreamreader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class T2 {
  public static void main(String args[]) {
    try {
      System.out.print("Enter your name: ");
      InputStreamReader reader = new InputStreamReader(System.in);
      BufferedReader in = new BufferedReader(reader);

      String name = in.readLine();
      System.out.println("Hello, " + name + ". Enter three ints...");
      int[] values = new int[3];
      double sum = 0.0;

      for (int i = 0; i < values.length; i++) {
        System.out.print("Number " + (i + 1) + ": ");
        String temp = in.readLine();
        values[i] = Integer.parseInt(temp);
        sum += values[i];
      }

      System.out.println("The average equals " + sum / values.length);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}