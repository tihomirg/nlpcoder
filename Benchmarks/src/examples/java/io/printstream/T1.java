package examples.java.io.printstream;
import java.io.File;
import java.io.PrintStream;

public class T1 {
  public static void main(String[] args) throws Exception {
    File file = new File("C:\\a.txt");
    PrintStream ps = new PrintStream(file);
    System.setOut(ps);
    System.out.println("To File");
  }
}