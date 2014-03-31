package examples.java.io.bufferedwriter;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class T2 {

  public static void main(String args[]) {
    try {
      FileWriter fw = new FileWriter(args[0]);

      BufferedWriter bw = new BufferedWriter(fw);

      for(int i = 0; i < 12; i++) {
        bw.write("Line " + i + "\n");
      }

      bw.close();
    }
    catch(Exception e) {
      System.out.println("Exception: " + e);
    }
  }
}
