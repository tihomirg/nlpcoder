package examples.java.io.filteroutputstream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

public class T1 {

  public static void main(String[] args) throws Exception {
    BufferedOutputStream bufferedOutput = new BufferedOutputStream(new FileOutputStream(
        "yourFile.txt"));
    bufferedOutput.write("Line one".getBytes());
    bufferedOutput.write("\n".getBytes());

    bufferedOutput.write(65);
    bufferedOutput.close();
  }
}