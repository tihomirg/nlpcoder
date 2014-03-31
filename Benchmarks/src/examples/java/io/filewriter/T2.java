package examples.java.io.filewriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class T2 {
  public static void main(String[] args) throws IOException {
    File inputFile = new File("farrago.txt");
    File outputFile = new File("outagain.txt");

    FileReader in = new FileReader(inputFile);
    FileWriter out = new FileWriter(outputFile);
    int c;

    while ((c = in.read()) != -1)
      out.write(c);

    in.close();
    out.close();
  }
}