package examples.java.util.enumset;
import java.util.EnumSet;
import java.util.Iterator;

public class T1 {
    
    public static void main(String[] args) {
        EnumSet largeSize = EnumSet.of(Size.XL,Size.XXL,Size.XXXL);
        for(Iterator it = largeSize.iterator();it.hasNext();){
            Size size = (Size)it.next();
            System.out.println(size);
        }
    }
}


enum Size {
  S, M, L, XL, XXL, XXXL;

}