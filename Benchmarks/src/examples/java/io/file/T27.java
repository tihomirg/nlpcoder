package examples.java.io.file;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class T27 {
  public static void main(String args[]) throws MalformedURLException {
    File file = new File("The End");
    URL url2 = file.toURI().toURL();
    System.out.printf("Good url %s%n", url2);
  }
}
