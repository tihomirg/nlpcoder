package dialogtest.handlers;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import api.Local;


public class Locals {

	private boolean isLocalized;
	private Stack<List<Local>> locals;

	public Locals() {
		this.locals = new Stack<List<Local>>();
	}

	public void localized() {
		isLocalized = true;
	}

	public void push() {
		if(!isLocalized){
			locals.push(new LinkedList<Local>());
		}
	}

	public void pop() {
		if(!isLocalized){
			locals.pop();
		}
	}

	public void add(Local local){
		if(!isLocalized){
			List<Local> top = locals.peek();
			top.add(local);
		}
	}
	
	public List<Local> getAll(){
		List<Local> res = new LinkedList<Local>();
		
		for (List<Local> localList: locals) {
			res.addAll(localList);
		}
		
		return res;
	}
}
