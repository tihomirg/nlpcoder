package examples.java.io.streamtokenizer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

public class T4 {
  public static void main(String args[]) throws IOException {
    BufferedReader inData = new BufferedReader(new InputStreamReader(System.in));
    StreamTokenizer inStream = new StreamTokenizer(inData);
    inStream.commentChar('#');
    boolean eof = false;
    do {
      int token = inStream.nextToken();
      switch (token) {
      case StreamTokenizer.TT_EOF:
        System.out.println("EOF encountered.");
        eof = true;
        break;
      case StreamTokenizer.TT_EOL:
        System.out.println("EOL encountered.");
        break;
      case StreamTokenizer.TT_WORD:
        System.out.println("Word: " + inStream.sval);
        break;
      case StreamTokenizer.TT_NUMBER:
        System.out.println("Number: " + inStream.nval);
        break;
      default:
        System.out.println((char) token + " encountered.");
        if (token == '!')
          eof = true;
      }
    } while (!eof);
  }
}