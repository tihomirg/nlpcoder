package examples.java.io.file;

import java.io.File;
import java.util.Date;

public class T24 {

  public static void main(String[] args) throws Exception {
    File fileToChange = new File("C:/myfile.txt");

    Date filetime = new Date(fileToChange.lastModified());
    System.out.println(filetime.toString());

    System.out.println(fileToChange.setLastModified(System.currentTimeMillis()));

    filetime = new Date(fileToChange.lastModified());
    System.out.println(filetime.toString());

  }
}
