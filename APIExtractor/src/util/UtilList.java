package util;

import java.util.LinkedList;
import java.util.List;

public class UtilList {
	
	public static <T> List<T> flatten(List<List<T>> lists) {
		List<T> rlist = new LinkedList<T>();
		
		for (List<T> list : lists) {
			rlist.addAll(list);
		}
		
		return rlist;
	}
}
