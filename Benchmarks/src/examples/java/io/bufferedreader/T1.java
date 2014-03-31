package examples.java.io.bufferedreader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class T1 {

  public static void main(String[] args) throws Exception {
    URL url = new URL("http://localhost:1776");
    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
    String line;
    while ((line = in.readLine()) != null) {
      System.out.println(line);
    }
    in.close();
  }
}