package examples.java.io.filereader;

import java.io.FileReader;

public class T3 {
  public static void main(String[] argv) throws Exception {
    FileReader fr = new FileReader("text.txt");
    int count;
    char chrs[] = new char[80];

    do {
      count = fr.read(chrs);
      for (int i = 0; i < count; i++)
        System.out.print(chrs[i]);
    } while (count != -1);

  }
}
