package examples.java.io.file;

import java.io.File;

public class T28 {
  public static void main(String[] a) throws java.net.MalformedURLException {
    java.net.URL u = new File("GetResource.java").toURL();
    System.out.println(u);
  }
}
