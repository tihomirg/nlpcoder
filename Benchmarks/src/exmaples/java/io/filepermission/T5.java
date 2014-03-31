package exmaples.java.io.filepermission;
import java.io.File;
import java.io.FileWriter;

public class T5 {
  public static void main(String[] args) throws Exception{
    File file = new File("user.txt");

    FileWriter writer = new FileWriter(file, true);
    writer.write("username=java;password=secret" + System.getProperty("line.separator"));
    writer.flush();
    writer.close();
  }
}