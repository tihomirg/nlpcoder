package examples.java.io.bufferedoutputstream;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class T1 {
  public static void main(String[] arguments) {
    int[] primes = new int[400];
    int numPrimes = 0;

    int candidate = 2;
    while (numPrimes < 400) {
      primes[numPrimes] = candidate;
      numPrimes++;
      candidate++;
    }

    try {
      FileOutputStream file = new FileOutputStream("p.dat");
      BufferedOutputStream buff = new BufferedOutputStream(file);
      DataOutputStream data = new DataOutputStream(buff);

      for (int i = 0; i < 400; i++)
        data.writeInt(primes[i]);
      data.close();
    } catch (IOException e) {
      System.out.println("Error - " + e.toString());
    }
  }
}