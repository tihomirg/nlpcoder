package examples.java.io.file;

import java.io.File;

public class T10 {

  public static void main(String[] args) throws Exception{

    new File("newFile.txt").createNewFile();
  }

}