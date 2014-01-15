package util;

public class List<T> extends java.util.LinkedList<T> {

	public List<T> f(T e){
		this.add(e);
		return this;
	}
	
	public List<T> f(List<T> e){
		this.addAll(e);
		return this;
	}
	
}
