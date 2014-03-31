package examples.java.io.file;
import java.io.File;

public class T17 {

  public static void main(String[] args) {
    File file = new File("C:");
    long totalSpace = file.getTotalSpace();
    System.out.println("Total space on " + file + " = " + totalSpace + "bytes");

    // Check the free space in C:
    long freeSpace = file.getFreeSpace();
    System.out.println("Free space on " + file + " = " + freeSpace + "bytes");
  }
}