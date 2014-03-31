package examples.java.io.bufferedreader;

import java.io.BufferedReader;
import java.io.FileReader;

public class T2 {

  public static void main(String args[]) {
    try {
      FileReader fr = new FileReader(args[0]);
      BufferedReader br = new BufferedReader(fr);

      String s;
      while((s = br.readLine()) != null)
        System.out.println(s);

      fr.close();
    }
    catch(Exception e) {
      System.out.println("Exception: " + e);
    }
  }
}