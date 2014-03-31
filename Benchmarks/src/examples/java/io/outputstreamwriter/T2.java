package examples.java.io.outputstreamwriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class T2 {
  public static void main(String[] args) {
    try {
      char[] chars = new char[2];
      chars[0] = '\u4F60';
      chars[1] = '\u597D';
      String encoding = "GB18030";
      File textFile = new File("C:\\temp\\myFile.txt");
      OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(textFile), encoding);
      writer.write(chars);
      writer.close();
    } catch (IOException e) {
      System.out.println(e.toString());
    }
  }
}
