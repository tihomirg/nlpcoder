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

	public static <T> List<List<T>> mapToSingletons(List<T> list) {
		List<List<T>> singletons = new LinkedList<List<T>>();
		
		for (T elem : list) {
			LinkedList<T> singleton = new LinkedList<T>();
			singleton.add(elem);
			singletons.add(singleton);
		}
		
		return singletons;
	}

	public static <T> List<T> merge(List<T> list1, List<T> list2) {
		LinkedList<T> list = new LinkedList<T>();
		list.addAll(list1);
		list.addAll(list2);
		return list;
	}
}
