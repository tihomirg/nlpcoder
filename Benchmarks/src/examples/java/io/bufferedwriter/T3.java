package examples.java.io.bufferedwriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class T3 {
  public static void main(String[] args) throws Exception {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    String strLine = in.readLine();
    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    out.write(strLine, 0, strLine.length());
    out.flush();
    in.close();
    out.close();
  }
}
