package test;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Skeleton implementation of City interface. Usable as-is or suitable for
 * extension.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 */

class NewClass<T>{}

public class CityImpl<A> extends NewClass<A> {
	
	public int[] f1;
	public String f2;
	public Map<Integer, String> f3;
	
	public int[] m1() {
		return null;
	}
	
	public String m2(){
		return null;
	}
	
	public List<? extends Object> m3(long a){
		return null;
	}
	
	public int[] m4(){
		return null;
	}
	
}
