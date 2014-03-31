package examples.java.io.randomaccessfile;
import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class T10 {
  public static void main(String[] args) throws Exception{
    ZipInputStream zipinputstream = new ZipInputStream(new FileInputStream("filename"));
    ZipEntry zipentry = zipinputstream.getNextEntry();
    while (zipentry != null) {
      String entryName = zipentry.getName();
      File newFile = new File(entryName);
      String directory = newFile.getParent();
      if (directory == null) {
        if (newFile.isDirectory())
          break;
      }
      RandomAccessFile  rf = new RandomAccessFile(entryName, "r");
      String line;
      if ((line = rf.readLine()) != null) {
        System.out.println(line);
      }
      rf.close();
      zipinputstream.closeEntry();
      zipentry = zipinputstream.getNextEntry();
    }
    zipinputstream.close();
  }
}