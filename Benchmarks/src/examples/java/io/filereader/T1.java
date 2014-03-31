package examples.java.io.filereader;
import java.io.FileReader;

public class T1 {

  public static void main(String args[]) {
    try {
      int counts[] = new int[10];
      FileReader fr = new FileReader(args[0]);

      int i;
      while ((i = fr.read()) != -1) {
        char c = (char) i;
        int k = c - '0';
        if (k >= 0 && k < 10)
          ++counts[k];
      }

      // Display digit counts
      for (int j = 0; j < 10; j++) {
        char c = (char) ('0' + j);
        System.out.print(c + "=");
        System.out.print(counts[j] + "; ");
      }

      fr.close();
    } catch (Exception e) {
      System.out.println("Exception: " + e);
    }
  }
}