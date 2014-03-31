package exmaples.java.io.filepermission;

import java.awt.AWTPermission;
import java.io.FilePermission;

public class T2 {
  public static void main(String args[]) throws Exception {
    SecurityManager sm = System.getSecurityManager();
    if (sm != null) {
      FilePermission fp = new FilePermission("c:\\autoexec.bat", "read");
      sm.checkPermission(fp);
    }

    if (sm != null) {
      AWTPermission ap = new AWTPermission("accessClipboard");
      sm.checkPermission(ap);
    }
    System.out.println("Has AWTPermission to access AWT Clipboard");

  }
}
