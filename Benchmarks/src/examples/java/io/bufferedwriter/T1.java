package examples.java.io.bufferedwriter;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;

public class T1 {
  public static void main(String[] args) throws Exception {
    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    System.out.println(stdin.readLine());
    BufferedReader in = new BufferedReader(new FileReader("Main.java"));
    String s, s2 = new String();
    while ((s = in.readLine()) != null)
      s2 += s + "\n";
    in.close();
    StringReader in1 = new StringReader(s2);
    int c;
    while ((c = in1.read()) != -1)
      System.out.print((char) c);
    BufferedReader in2 = new BufferedReader(new StringReader(s2));
    PrintWriter out1 = new PrintWriter(new BufferedWriter(new FileWriter(
        "IODemo.out")));
    int lineCount = 1;
    while ((s = in2.readLine()) != null)
      out1.println(lineCount++ + ": " + s);
    out1.close();
  }
}