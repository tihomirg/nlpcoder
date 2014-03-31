package examples.java.io.randomaccessfile;
import java.io.RandomAccessFile;

public class T5 {
  public static void main(String[] args) throws Exception {
    RandomAccessFile randomAccessFile = null;

    String line1 = "line\n";
    String line2 = "asdf1234\n";

    // read / write permissions
    randomAccessFile = new RandomAccessFile("yourFile.dat", "rw");

    randomAccessFile.writeBytes(line1);
    randomAccessFile.writeBytes(line2);

    // Place the file pointer at the end of the first line
    randomAccessFile.seek(line1.length());

    byte[] buffer = new byte[line2.length()];
    randomAccessFile.read(buffer);
    System.out.println(new String(buffer));

    randomAccessFile.close();
  }
}
