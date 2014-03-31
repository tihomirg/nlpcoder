package exmaples.java.io.filepermission;
import java.io.FilePermission;
import java.security.AccessController;

public class T1 {
  public static void main(String args[]) throws Exception {
    FilePermission fp = new FilePermission("c:\\autoexec.bat", "read");
    AccessController.checkPermission(fp);

  }
}