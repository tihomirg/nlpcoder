package examples.java.util.arrays;
public class T19{

  public static void main(String[] arg){
     char[] message = new char[50];
     java.util.Arrays.fill(message, 'A');
     for(char ch: message){
       System.out.println(ch);
     }
  }
} 