package examples.java.io.file;

import java.io.File;

public class T14 {
  public static void main(String[] args) {

    // Create an object that is a directory
    File myFile = new File("test.txt");
    System.out.println(myFile + (myFile.isHidden() ? " is" : " is not")
                       + " hidden");
  }
}