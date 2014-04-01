package examples.java.io.filepermission;
import java.io.FileReader;
import java.io.FileWriter;

//copy file
public class T3 {

  public static void main(String args[]) {

    try {

      FileReader fr = new FileReader(args[0]);

      FileWriter fw = new FileWriter(args[1]);

      int i;
      while ((i = fr.read()) != -1) {
        fw.write(i);
      }
      fw.close();

      fr.close();
    } catch (Exception e) {
      System.out.println("Exception: " + e);
    }
  }
}        